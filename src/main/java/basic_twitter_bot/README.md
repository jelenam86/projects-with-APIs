# basic-twitter-bot
Project "Watch for new TIL facts" from [beginner-projects by Jorge Gonzales](https://github.com/jorgegonzalez/beginner-projects)

# Important 
In order to run MainApp, you need to obtain your [access tokens for twitter API](https://developer.twitter.com/). Once you do that, fill in //TODO fields in src/main/java/basic_twitter_bot/resources/twitter4j.properties.
<br>Other solution is to run NoTwitterMain. In that case, no tweets are posted, app only retrieve reddit data and print them to the console and to file as well.

# [Project text](https://github.com/jorgegonzalez/beginner-projects#watch-for-new-til-facts):

Create a program that receives data from the [/r/todayilearned](https://www.reddit.com/r/todayilearned/) subreddit, and looks for new facts that have been posted.
<br>Each time the program comes across a new fact, the fact should be printed into the command line. However, phrases like "TIL ", "TIL that", etc should be removed so the only thing that is printed is the fact.

[New TIL API data here](https://www.reddit.com/r/todayilearned/new/.json)

There are a couple things to note about this since you'll more than likely be using a loop to check for new posts. According to Reddit's [API Access Rules Page](https://github.com/reddit-archive/reddit/wiki/API), the API pages are only updated once every thirty seconds, so you'll have to have your code pause for at least thirty seconds before it tries to find more posts. Secondly, if for some reason you decide to try to get data sooner than every thirty seconds, make sure to not send more than thirty requests per minute. That is the maximum you are allowed to do.
<br>There is actually a lot you can do once your program starts receiving facts. Instead of simply printing the facts, here are some ideas for what you can do with them. If you currently do not feel like you can accomplish these ideas, feel free to come back later when you have more experience.
<ul>
<li>Print the link to the source of the fact too.</li>
<li>Try to further clean up the fact by adding punctuation to the end if it is missing, capitalize the first word, etc.</li>
<li>Write the facts to a separate text file so you end up with a giant compilation of random facts.</li>
<li>Create a bot that posts the facts to twitter.</li>
</ul>
Remember, the maximum amount of characters you can use in a tweet is only 140, so you'll have to filter out facts that are longer than that.
