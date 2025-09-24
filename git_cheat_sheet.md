# 🐙 Git Workflow Cheat Sheet

A handy reference for everyday Git commands — branching, committing, merging, and syncing with `main` when co-developing.

---

## 🔧 Setup (one-time per repo)
```bash
git config --global user.name "Your Name"
git config --global user.email "your@email.com"

# Check config
git config --list
```

---

## 📍 Navigation & Info
```bash
git status                     # Show changes (staged, unstaged, untracked)
git log --oneline --graph      # Compact commit history with branches
git rev-parse --show-toplevel  # Show project root
```

---

## 💾 Basic Workflow
```bash
git add <file>            # Stage a specific file
git add .                 # Stage everything in the folder
git commit -m "Message"   # Commit staged changes
git push                  # Push commits to remote branch
git pull                  # Pull latest changes from remote
```

---

## 🌱 Branching
```bash
git branch                # List branches
git branch <name>         # Create a new branch
git checkout <name>       # Switch to branch
git checkout -b <name>    # Create & switch in one step
git branch -d <name>      # Delete a branch (if merged)
```

---

## 🔀 Merging
```bash
# Make sure you’re on the branch you want to merge into!
git checkout main
git pull origin main       # Update local main with latest remote changes
git merge <branch-name>    # Merge feature branch into current branch
```

If there are conflicts:
```bash
# Edit files to resolve conflict markers (<<<<, ====, >>>>)
git add <fixed-file>
git commit                 # Finalize merge
```

---

## 🔄 Staying Up-to-Date with Main
```bash
git checkout main
git pull origin main       # Get the latest main branch
git checkout <feature>     # Switch to your branch
git merge main             # Merge main into your branch (keeps it fresh)
```

---

## 🚑 Undo / Fix Mistakes
```bash
git restore <file>          # Undo unstaged changes in a file
git restore --staged <file> # Unstage a file
git reset --hard HEAD       # Reset everything to last commit (⚠️ dangerous)
git checkout -- <file>      # Discard changes in file
```

---

## 🌐 Remote Basics
```bash
git remote -v               # Show remote URLs
git push -u origin <branch> # Push new branch to remote for first time
```

---

### 🔄 Resetting local repository to match remote

If your local repository becomes out of sync (e.g., files missing, or you just want to force a clean copy), you can **force your local branch to match the latest version on GitHub**:

```bash
git fetch origin
git reset --hard origin/main
```


## ✅ Golden Workflow for Co-Developing
1. `git checkout main`  
2. `git pull origin main` (stay updated)  
3. `git checkout -b feature-xyz` (work on a new branch)  
4. `git add . && git commit -m "work in progress"`  
5. `git push -u origin feature-xyz`  
6. Open pull request → merge → repeat.  

