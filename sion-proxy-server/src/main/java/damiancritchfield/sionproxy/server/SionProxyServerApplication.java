package damiancritchfield.sionproxy.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import damiancritchfield.sionproxy.server.netty.server.SionProxyServer;

@SpringBootApplication
public class SionProxyServerApplication implements CommandLineRunner {

    @Autowired
    private SionProxyServer sionProxyServer;

    private static final Logger logger = LoggerFactory.getLogger(SionProxyServerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SionProxyServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("starting sion proxy server...");
        sionProxyServer.start();
        logger.info("sion proxy server started");
    }
}
