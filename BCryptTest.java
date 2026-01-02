import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String password = "admin123";

        // 生成新的加密密码
        String encodedPassword = encoder.encode(password);
        System.out.println("原始密码: " + password);
        System.out.println("加密后的密码: " + encodedPassword);

        // 验证当前使用的密码
        String currentHash = "$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi";
        boolean matches = encoder.matches(password, currentHash);
        System.out.println("\n验证当前密码 " + password + " 是否匹配:");
        System.out.println("密码哈希: " + currentHash);
        System.out.println("匹配结果: " + matches);

        // 生成几个新的密码供选择
        System.out.println("\n生成新的 BCrypt 密码（每次都不同）：");
        for (int i = 1; i <= 3; i++) {
            System.out.println(i + ". " + encoder.encode(password));
        }
    }
}
