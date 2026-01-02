import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GenerateNewPassword {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "admin123";

        System.out.println("生成新的 BCrypt 密码哈希...\n");

        // 生成5个新的哈希用于测试
        for (int i = 1; i <= 5; i++) {
            String hash = encoder.encode(password);
            boolean matches = encoder.matches(password, hash);
            System.out.println("哈希 " + i + ": " + hash);
            System.out.println("验证结果: " + matches);
            System.out.println();
        }

        // 测试当前数据库中的哈希
        String currentHash = "$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi";
        System.out.println("测试当前数据库密码哈希:");
        System.out.println("哈希: " + currentHash);
        System.out.println("验证 'admin123': " + encoder.matches("admin123", currentHash));
        System.out.println("验证 'Admin123': " + encoder.matches("Admin123", currentHash));
        System.out.println("验证 'ADMIN123': " + encoder.matches("ADMIN123", currentHash));
    }
}
