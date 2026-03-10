import os

log_path = r"c:\Users\raviv\AndroidStudioProjects\MyApplication7\verbose_log.txt"
if os.path.exists(log_path):
    # Depending on powershell version, it could be utf-16 or utf-8
    try:
        with open(log_path, 'r', encoding='utf-16') as f:
            lines = f.readlines()
    except UnicodeDecodeError:
        with open(log_path, 'r', encoding='utf-8') as f:
            lines = f.readlines()
            
    # Search for anything looking like a Room error
    for i, line in enumerate(lines):
        if "error:" in line.lower() or "exception:" in line.lower() or "room cannot" in line.lower() or "e: " in line:
            print("FOUND ERROR at line", i)
            # Print 5 lines before and 10 lines after
            start = max(0, i - 5)
            end = min(len(lines), i + 10)
            for j in range(start, end):
                print(lines[j].strip())
            print("-" * 40)
            
    print("Log parsing complete.")
else:
    print("Log file not found.")
