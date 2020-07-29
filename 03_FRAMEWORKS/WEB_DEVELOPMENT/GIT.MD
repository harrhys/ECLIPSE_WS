- Global Configuration for Git

git --version

- To configure your user name to be used by Git, type the following at the prompt:

git config --global user.name "Your Name"

- To configure your email to be used by Git, type the following at the prompt:

git config --global user.email <your email address>

- You can check your default Git global configuration, you can type the following at the prompt:

git config --list

-To initialize the folder as a Git repository

git init

- Checking your Git repository status
Type the following at the prompt to check your Git repository's status:

git status

- Adding files to the staging area

To add files to the staging area of your Git repository, type:

git add .

- Commiting to the Git repository

To commit the current staging area to your Git repository, type:

git commit -m "first commit"

- Checking the log of Git commits

To check the log of the commits to your Git repository, type

git log --oneline

- Set the local Git repository to set its remote origin

type the following to set up your local repository to link to your online Git repository:

git remote add origin <repository URL>

- Pushing your commits to the online repository

type the following to push the commits to the online repository:

git push -u origin master

- Cloning an online repository

To clone an online repository to your computer, type the following at the prompt:

git clone <repository URL>
