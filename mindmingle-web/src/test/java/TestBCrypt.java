import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestBCrypt {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String password = "admin123";
        String oldHash = "$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi";

        // 测试旧哈希
        System.out.println("=== 测试旧哈希 ===");
        System.out.println("密码: " + password);
        System.out.println("旧哈希: " + oldHash);
        System.out.println("验证结果: " + encoder.matches(password, oldHash));

        // 生成新哈希
        System.out.println("\n=== 生成新哈希 ===");
        for (int i = 1; i <= 3; i++) {
            String newHash = encoder.encode(password);
            boolean matches = encoder.matches(password, newHash);
            System.out.println("\n新哈希 " + i + ":");
            System.out.println(newHash);
            System.out.println("验证: " + matches);
        }
    }
}
