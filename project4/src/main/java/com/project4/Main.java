/*
 * Copyright 2010 Proofpoint, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.project4;

import com.google.inject.Injector;
import com.project4.bootstrap.Bootstrap;
import com.project4.discovery.client.Announcer;
import com.project4.discovery.client.DiscoveryModule;
import com.project4.event.client.HttpEventModule;
import com.project4.jmx.JmxHttpModule;
import com.project4.jmx.http.rpc.JmxHttpRpcModule;
import com.project4.json.JsonModule;
import com.project4.http.server.HttpServerModule;
import com.project4.jaxrs.JaxrsModule;
import com.project4.jmx.JmxModule;
import com.project4.log.LogJmxModule;
import com.project4.log.Logger;
import com.project4.node.NodeModule;
import com.project4.tracetoken.TraceTokenModule;
import org.weakref.jmx.guice.MBeanModule;

public class Main
{
    private final static Logger log = Logger.get(Main.class);

    public static void main(String[] args)
            throws Exception
    {
        Bootstrap app = new Bootstrap(
                new NodeModule(),
                new DiscoveryModule(),
                new HttpServerModule(),
                new JsonModule(),
                new JaxrsModule(),
                new MBeanModule(),
                new JmxModule(),
                new JmxHttpModule(),
                new JmxHttpRpcModule(),
                new LogJmxModule(),
                new HttpEventModule(),
                new TraceTokenModule(),
                new MainModule());

        try {
            Injector injector = app.strictConfig().initialize();
            injector.getInstance(Announcer.class).start();
        }
        catch (Throwable e) {
            log.error(e);
            System.exit(1);
        }
    }
}
