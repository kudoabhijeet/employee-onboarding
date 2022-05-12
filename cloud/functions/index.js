/* eslint-disable no-var */
const functions = require("firebase-functions");
// const xlsx = require("xlsx");
const admin = require("firebase-admin");
admin.initializeApp();

// https://firebase.google.com/docs/functions/write-firebase-functions

exports.addUserToFirestore = functions.auth.user().onCreate((user) => {
  var usersRef = admin.firestore().collection("users");
  return usersRef.doc(user.uid).set({
    displayName: user.displayName,
    email: user.email,
    company: "",
  });
});

