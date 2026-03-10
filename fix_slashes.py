import os
import glob

directory = r"c:\Users\raviv\AndroidStudioProjects\MyApplication7\app\src\main\java\com\simats\siddha\myapplication"
files = glob.glob(os.path.join(directory, "*.kt"))

for file in files:
    with open(file, 'r', encoding='utf-8') as f:
        content = f.read()
    
    original = content
    content = content.replace(r'\"\", \"\"', '"", ""')
    
    # Also fix RiskAnalysisScreen dummyApi name
    if "UserViewModel(com.simats.siddha.myapplication.api.Repository(dummyApi))" in content:
        content = content.replace("UserViewModel(com.simats.siddha.myapplication.api.Repository(dummyApi))", "UserViewModel(com.simats.siddha.myapplication.api.Repository(dummyApi), com.simats.siddha.myapplication.api.AuthManager(androidx.compose.ui.platform.LocalContext.current))")

    if original != content:
        with open(file, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f"Fixed {os.path.basename(file)}")
