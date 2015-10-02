package nikhanch.com.sfbandroidchatbubbles.ApplicationService.CommunicationManager;

public class AutoReply {

    private static String[] mText = {
        "You will become great if you believe in yourself.",
        "Never give up on someone that you don't go a day without thinking about.",
        "Today it's up to you to create the peacefulness you long fo.",
        "A friend asks only for your time not your mone.",
        "If you refuse to accept anything but the best, you very often get i.",
        "A smile is your passport into the hearts of other.",
        "A good way to keep healthy is to eat more Chinese foo.",
        "Your high-minded principles spell success",
        "Hard work pays off in the future, laziness pays off no.",
        "Change can hurt, but it leads a path to something better.",
        "Enjoy the good luck a companion brings yo.",
        "People are naturally attracted to yo.",
        "Hidden in a valley beside an open stream- This will be the type of place where you will find your drea.",
        "A chance meeting opens new doors to success and friendshi.",
        "You learn from your mistakes... You will learn a lot toda.",
        "If you have something good in your life, don't let it g.",
        "What ever you're goal is in life, embrace it visualize it, and for it will be your.",
        "Your shoes will make you happy toda.",
        "You cannot love life until you live the life you lov.",
        "Be on the lookout for coming events; They cast their shadows beforehan.",
        "Land is always on the mind of a flying bir.",
        "The man or woman you desire feels the same about yo.",
        "Meeting adversity well is the source of your strengt.",
        "A dream you have will come tru.",
        "Our deeds determine us, as much as we determine our deed.",
        "Never give up. You're not a failure if you don't give u.",
        "You will become great if you believe in yoursel.",
        "There is no greater pleasure than seeing your loved ones prospe.",
        "You will marry your love.",
        "A very attractive person has a message for yo.",
        "You already know the answer to the questions lingering inside your hea.",
        "It is now, and in this world, that we must liv.",
        "You must try, or hate yourself for not tryin.",
        "You can make your own happines.",
        "The greatest risk is not taking on.",
        "The love of your life is stepping into your planet this summe.",
        "Love can last a lifetime, if you want it t.",
        "Adversity is the parent of virtu.",
        "Serious trouble will bypass yo.",
        "A short stranger will soon enter your life with blessings to shar.",
        "Now is the time to try something ne.",
        "Wealth awaits you very soo.",
        "If you feel you are right, stand firmly by your conviction.",
        "If winter comes, can spring be far behin.",
        "Keep your eye out for someone specia.",
        "You are very talented in many way.",
        "A stranger, is a friend you have not spoken to ye.",
        "A new voyage will fill your life with untold memorie.",
        "You will travel to many exotic places in your lifetim.",
        "Your ability for accomplishment will follow with succes.",
        "Nothing astonishes men so much as common sense and plain dealin.",
        "Its amazing how much good you can do if you dont care who gets the credi."};

    static private int mIndex = 0 ;
    static private int mSize = AutoReply.mText.length;


    public static String getText()
    {
        return AutoReply.mText[++AutoReply.mIndex % AutoReply.mSize];
    }
}
