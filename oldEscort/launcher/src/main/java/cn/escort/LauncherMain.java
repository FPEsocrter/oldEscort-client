package cn.escort;

import cn.escort.launcher.args.ArgsUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableJpaAuditing
@SpringBootApplication
@EnableScheduling
public class LauncherMain {
    public static void main(String[] args) {
        args = new ArgsUtils().run(args);
        SpringApplication.run(LauncherMain.class, args);
    }


}