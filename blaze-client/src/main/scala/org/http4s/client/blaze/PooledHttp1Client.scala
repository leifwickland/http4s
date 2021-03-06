package org.http4s.client.blaze

import java.nio.channels.AsynchronousChannelGroup
import java.util.concurrent.ExecutorService
import javax.net.ssl.SSLContext

import scala.concurrent.duration.Duration


/** Create a HTTP1 client which will attempt to recycle connections */
object PooledHttp1Client {

  /** Construct a new PooledHttp1Client */
  def apply(maxPooledConnections: Int = 10,
                         timeout: Duration = DefaultTimeout,
                      bufferSize: Int = DefaultBufferSize,
                        executor: ExecutorService = ClientDefaultEC,
                      sslContext: Option[SSLContext] = None,
                           group: Option[AsynchronousChannelGroup] = None) = {
    val http1 = new Http1Support(bufferSize, timeout, executor, sslContext, group)
    val pool = new PoolManager(maxPooledConnections, http1)
    new BlazeClient(pool)
  }
}
