import subprocess
import os

repo_dir = r"c:\Users\raviv\AndroidStudioProjects\MyApplication7"
log_file = os.path.join(repo_dir, "clean_log.txt")

result = subprocess.run([os.path.join(repo_dir, "gradlew.bat"), "app:kaptDebugKotlin"], cwd=repo_dir, capture_output=True, text=True, encoding="utf-8", errors="replace")

with open(log_file, "w", encoding="utf-8") as f:
    f.write(result.stdout)
    f.write("\n=== STDERR ===\n")
    f.write(result.stderr)
    
print("Log written to clean_log.txt")
