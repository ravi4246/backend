import os, glob

for filepath in glob.glob('c:/Users/raviv/AndroidStudioProjects/MyApplication7/app/src/main/java/**/*.kt', recursive=True):
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()
    
    content = content.replace(
        'listOf(0.9f, 0.82f), emptyList(), "Keep up good habits", null, emptyList())',
        'listOf(0.9f, 0.82f), emptyList(), "Keep up good habits", null, 8, 21, 5, emptyList())'
    )
    content = content.replace(
        'recommendation = "Keep it up", recommendedPlan = null, insights = emptyList())',
        'recommendation = "Keep it up", recommendedPlan = null, daysLogged = 8, totalDays = 21, streak = 5, insights = emptyList())'
    )
    
    with open(filepath, 'w', encoding='utf-8') as f:
        f.write(content)

print("Replacement complete.")
