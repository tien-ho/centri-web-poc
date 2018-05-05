package com.rallyhealth.dataship.reader.controllers

import java.io.File
import java.security.interfaces.RSAPrivateKey
import java.security.{KeyPair, KeyPairGenerator}
import java.util.Base64
import java.util.concurrent.atomic.AtomicInteger

import akka.stream.Materializer
import com.miguno.akka.testing.{MockScheduler, VirtualTime}
import com.rallyhealth.dataship.reader.configs.DatashipReaderConfig
import com.rallyhealth.dataship.reader.configs.DatashipReaderConfig.Defaults
import com.rallyhealth.dataship.reader.crypto.KeyDecryptionKey
import com.rallyhealth.dataship.reader.services.{KafkaReader, RallyKafkaConsumersService, RallyKafkaConsumersServiceImpl}
import com.rallyhealth.dataship.reader.setup.OneAppPerSuiteWithComponents
import com.rallyhealth.illuminati.v9.BaseSecretConfig
import com.rallyhealth.spartan.v2.config.{MemoryRallyConfig, RallyConfig}
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.mockito.invocation.InvocationOnMock
import org.mockito.ArgumentMatchers._
import org.mockito.Mockito._
import org.mockito.stubbing.Answer
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll}
import org.scalatestplus.play.PlaySpec
import play.api.mvc._
import play.api.test.Helpers._
import play.api.test._

import scala.concurrent.{ExecutionContext, Future}

class KafkaReaderControllerSpec extends PlaySpec
  with OneAppPerSuiteWithComponents
  with MockitoSugar
  with BeforeAndAfter
  with BeforeAndAfterAll {
  implicit val ec: ExecutionContext = ExecutionContext.global
  val readRoute = "/offset"
  implicit lazy val materializer: Materializer = app.materializer

  class Fixture {
    val configFile = new File("test/resources/dataship_test.conf")
    val rallyConfig = RallyConfig(configFile)

    val secretConfig: BaseSecretConfig = BaseSecretConfig.fromTemplateFilename(rallyConfig, Defaults.secretTemplatePath)
    val datashipReaderConfig = new DatashipReaderConfig(rallyConfig, secretConfig)

    val mockKafkaConsumer = List(mock[KafkaConsumer[String, Array[Byte]]])
    val mockKafkaReader = mock[KafkaReader]
    val timer = new VirtualTime
    val mockScheduler: MockScheduler = timer.scheduler

    val pairGenerator = KeyPairGenerator.getInstance("RSA")
    pairGenerator.initialize(4096)
    val pair: KeyPair = pairGenerator.generateKeyPair()
    val privateKey: RSAPrivateKey = pair.getPrivate.asInstanceOf[RSAPrivateKey]

    val numReaders = 1

    val rallyKafkaConsumersService = mock[RallyKafkaConsumersServiceImpl]
    when(rallyKafkaConsumersService.numReaders).thenReturn(numReaders)

    val kafkaReaderController = new KafkaReaderControllerImpl
  }

  s"A call to ${readRoute}" should {
    "return latest offset" in {
      val f = new Fixture
      import f._
      val result: Future[Result] = call(kafkaReaderController.getOffset, FakeRequest("GET", readRoute))
      val bodyText: String = contentAsString(result)
      assert(Defaults.kafkaKeyDecryptionLocation == datashipReaderConfig.kafkaKeyDecryptionKeyLocation)
      assert(bodyText == "OK")
    }
  }
}
