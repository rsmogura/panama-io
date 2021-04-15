/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package eu.smogura.panama.posixio;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.net.ServerSocketFactory;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Timeout;
import org.openjdk.jmh.annotations.Warmup;

@State(Scope.Thread)
@Fork(jvmArgsPrepend = {
    "--enable-native-access", "eu.smogura.panama.io.posix,ALL-UNNAMED"
},
value = 3)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
public abstract class SocketReadCommon {
    private Socket clientSocket;
    private InputStream clientInputStream;
    private byte[] inputBuff4k = new byte[4096];
    private byte[] inputBuff16b = new byte[16];

    private ServerSocket server;
    private byte[] sendBuff = new byte[8192*4];
    private volatile boolean cont = true;
    private Future<Object> serverThread;
    private ExecutorService executorService;

    @Setup
    public void setup() throws Exception {
        final var port = (short) (new Random().nextInt(1000) + 20000);
        this.server = ServerSocketFactory.getDefault().createServerSocket(port);
        Arrays.fill(sendBuff, (byte) 1);
        CountDownLatch lock = new CountDownLatch(1);

        executorService = Executors.newSingleThreadExecutor();
        serverThread = executorService.submit(() -> {
            lock.countDown();
            final var conn = server.accept();
            final var out = conn.getOutputStream();
            while (cont) {
                out.write(sendBuff);
            }
            return null;
        });
        lock.await();
        this.clientSocket = createReadSocket(port);
        this.clientInputStream = this.clientSocket.getInputStream();
    }

    protected abstract Socket createReadSocket(short port) throws Exception ;

    @TearDown
    public void stop() throws Exception {
        cont = false;
        clientSocket.close();
        server.close();
        executorService.shutdownNow();
    }

    @Benchmark
    public void teatRead4k() throws Exception {
        clientInputStream.read(inputBuff4k);
    }

    @Benchmark
    public void testRead16b() throws Exception {
        clientInputStream.read(inputBuff16b);
    }

    @Benchmark
    public void testRead8bOffset() throws Exception {
        clientInputStream.read(inputBuff16b, 2, 8);
    }
}
