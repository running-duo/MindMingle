package com.aizz.mindmingle;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(EnableEncryptableProperties.class)
@SpringBootTest
class MindMingleApplicationTests {

    /**
     * 数据源配置
     */
    private static final DataSourceConfig DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder("jdbc:mysql://81.69.252.112:3306/mind_mingle?serverTimezone=Asia/Shanghai"
            , "root", "123456")
            .build();

    @Test
    void contextLoads() {
    }

    /**
     * mybatis p 代码生成
     */
    @Test
    void generateMapper() {
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        GlobalConfig globalConfig = new GlobalConfig.Builder().author("shiori").build();
        StrategyConfig strategyConfig = new StrategyConfig.Builder()
                .enableCapitalMode()
                .enableSkipView()
                .disableSqlFilter()
                .addInclude("bis_all_fire_alarm_file")
                .entityBuilder()
                .enableLombok()
                .enableFileOverride()
                .build();
        PackageConfig packageConfig = new PackageConfig.Builder()
                .parent("com.aizz")
                .entity("model")
                .service("service")
                .serviceImpl("service.impl")
                .mapper("com/aizz/mapper")
                .controller("controller")
                .build();
        generator.strategy(strategyConfig);
        generator.global(globalConfig);
        generator.packageInfo(packageConfig);
        generator.execute();
    }
}
