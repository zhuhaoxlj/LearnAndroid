# Learn Android

## 开发环境要求

为确保项目在所有开发者电脑上都能正常运行，请确保您的开发环境符合以下要求：

### 必要版本

- **JDK**: 17 (项目强制使用此版本)
- **Kotlin**: 1.9.0
- **Gradle**: 8.2
- **Gradle Plugin**: 8.2.2
- **Android SDK**: compileSdk 34, minSdk 24, targetSdk 34

### 环境设置

1. **安装JDK 17**
   - 确保环境变量`JAVA_HOME`指向JDK 17安装路径
   - 或在系统中安装JDK 17到默认路径：`/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home`

2. **使用项目内置的Gradle Wrapper**
   - 项目使用Gradle Wrapper确保Gradle版本一致
   - 运行命令始终使用`./gradlew`而非全局的`gradle`命令

3. **版本冲突解决**
   - 项目配置了`-Xskip-metadata-version-check`以处理潜在的Kotlin版本冲突
   - 依赖版本已在顶级build.gradle中集中管理

### 首次设置

```bash
# 检查JDK版本
java -version

# 确保使用Gradle Wrapper
./gradlew --version

# 清理并构建项目
./gradlew clean build
```

## 注意事项

- 不要在IDE中覆盖项目的Gradle或Kotlin版本设置
- 添加新依赖时请检查版本兼容性，优先使用项目定义的版本变量
- 如遇构建问题，请先尝试`./gradlew clean`和`./gradlew --refresh-dependencies` 