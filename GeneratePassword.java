import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeneratePassword {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "Xiaofeng2026.";

        System.out.println("===============================================");
        System.out.println("BCrypt 密码生成");
        System.out.println("===============================================");
        System.out.println("原始密码: " + password);
        System.out.println();
        System.out.println("BCrypt 加密后的密码:");

        // 生成3个不同的哈希值（每次生成都不同）
        for (int i = 1; i <= 3; i++) {
            String hash = encoder.encode(password);
            System.out.println(i + ". " + hash);

            // 验证
            boolean matches = encoder.matches(password, hash);
            System.out.println("   验证结果: " + (matches ? "✓ 正确" : "✗ 错误"));
            System.out.println();
        }

        System.out.println("===============================================");
        System.out.println("提示：以上任意一个密码都可以使用");
        System.out.println("推荐使用第1个");
        System.out.println("===============================================");
    }
}
