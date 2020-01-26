const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
exports.sendNotification = functions.https.onRequest((request, res) => {
    message = {
    "notification":{
      "title":request.query.title,
      "body":request.query.body
    },
    "condition": "!('anytopicyoudontwanttouse' in topics)"
    //~ "condition": "('weather' in topics)"
    //~ topic: "weather"
    }   

    admin.messaging().send(message)
      .then((response) => {
        // Response is a message ID string.
        console.log('Successfully sent message:', response);
        res.send ('Successfully sent message:'+ response);
      })
      .catch((error) => {
        res.send ('Error sending message:'+ error);
      });

}
);


exports.sendNotificationCallable = functions.https.onCall((request, res) => {
    console.log (request)
    message = {
    "notification":{
      "title":request.title,
      "body":request.body
    },
    "condition": "!('anytopicyoudontwanttouse' in topics)"
    //~ "condition": "('weather' in topics)"
    //~ topic: "weather"
    }   

    admin.messaging().send(message)
      .then((response) => {
        // Response is a message ID string.
        console.log('Successfully sent message:', response);
      })
      .catch((error) => {
        console.log ('Error sending message:'+ error);
      });

}
);

