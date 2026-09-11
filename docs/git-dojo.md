# Git Dojo — my recovery notes

> Part D of Lab 2. For each drill: the command(s) you ran, **one sentence in your own
> words** on what it did, and one on when you would reach for it again.
>
> Graded on the sentences, not the commands. Commands can be copied; understanding cannot.

## The three trees — in my own words

| Tree | What lives here |
|---|---|
| Working Directory |  |
| Staging Area (Index) |  |
| HEAD |  |

---

## Drill 1 — Committed to `main` by accident

**Commands I ran:** 
```bash
git switch main
echo "oops" > accident.txt
git add accident.txt
git commit -m 
git switch -c fix/rescued-work 
git switch main
git reset --hard origin/main
```
**What it did:** 
accidently committing a branch to my main and learning how to get it out of my main and back out into its own branch.

**When I would use it again:**
I would probably use this when I accidently put things into my main branch and want to get it out.

---

## Drill 2 — Wrong commit message / forgot a file

**Commands I ran:**
```bash
echo "x" > note.txt && git add note.txt && git commit -m "asdf"
git commit --amend -m "docs: add note file"
```
**What it did:**
it created a committed in the main tree with a bad name and is missing a file but with the second code we can rewrite the message and add the other file.

**Why you must not do this to a commit you already pushed:**
If you do this to a commit you have already pushed then it will rewrite its history.
---

## Drill 3 — Committed a file that should be ignored

**Commands I ran:**
```bash
mkdir -p target && echo "junk" > target/Main.class
git add -f target/Main.class && git commit -m "chore: oops, committed build output"
git rm -r --cached target # stop tracking, keep the local files
echo "target/" >> .gitignore
git add .gitignore && git commit -m "chore: untrack build output and ignore target/"
```
**What it did:**
It shows what would happen if you were to commit a file that should have been ignored.

**Why adding it to `.gitignore` alone was not enough:**
it is not enough because gitignore only ignores files Git isn't already tracking. So once it is tracked it must be untracked first.
---

## Drill 4 — Merge conflict

**Commands I ran:**
```bash
git switch -c feature/a
printf '# DungeonForge - branch A title\n' > README.md
git commit -am "docs: title from branch A"
git switch main
git switch -c feature/b
printf '# DungeonForge - branch B title\n' > README.md
git commit -am "docs: title from branch B"
git switch main
git merge feature/a # clean
git merge feature/b # CONFLICT
<<<<<<< HEAD
# DungeonForge - branch A title
=======
# DungeonForge - branch B title
>>>>>>> feature/b
```
**In the conflict markers, which side was "mine"?**
In between the <<< and=== lines is what you already have. But the === and >>> is what is arriving.

**What it did:**
It made two separate branches A and B from the main branch, you need to merge both of those branches A and B into the main one but only will be able to go through without problems.

**How I would back out of a merge I regretted starting:**
If you decided to change your mind about merging then all you have to do is type _**git merge --abort**_ and it reverts everything back.

---

## Drill 5 — "I destroyed everything"

**Commands I ran:**
```bash
git log --oneline # note the current hash
git reset --hard HEAD~3 # nuke the last three commits
git log --oneline # gone
git reflog # every position HEAD has held
git reset --hard <hash-from-before>
git checkout HEAD~2 # "You are in 'detached HEAD' state"
git switch - # back
git rebase -i HEAD~3 # change 'pick' to 'squash' on lines 2 and 3
```
**What `git reflog` showed me:**
It records where your HEAD has been in the last 90 days and that also includes delete commits too.

**One sentence on why this changes how nervous I should be about Git:**
It goes to show that anything that you commit is never lost and being able to know that makes it a little more reasureing for you.

---

## Stretch — Drill 6 (detached HEAD, interactive rebase)

**Notes:**

---

## The one command I want to remember from today

