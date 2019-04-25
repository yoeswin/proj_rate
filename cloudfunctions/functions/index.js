const functions = require('firebase-functions');
const admin = require("firebase-admin");
admin.initializeApp(functions.config().firebase);

//
// exports.timer = functions.database.ref('/root/Comment/{names}/text')
//     .onCreate((snapshot, context) => {
//       const original = context.params.names;
//       const mod = Date.now();
//       return snapshot.ref.parent.parent.child(original).child('timestamp').set(mod);
//     });


    exports.simpleDbFunction = functions.database.ref('/root/Comment/{commentId}')
        .onCreate((snap, context) => {
              var data = snap.val();
              var ref =  admin.database().ref("root/user/"+data.uid);
              ref.once("value", function(snapshot) {
                const count = snapshot.val().count;
                if(count===5){
                  ref =  admin.database().ref("root/Comment");
                  ref.orderByChild("uid").equalTo(data.uid).once("value",function(Snapshot) {
                    var min=0;
                    var key;
                    Snapshot.forEach(userSnapshot => {
                        var k = userSnapshot.key;
                        var timestam = userSnapshot.val().timestamp;
                          if((min>timestam)||(min===0)){
                            min = timestam ; 
                            key=k;
                          }
                        });
                         admin.database().ref("root/Comment/"+key).remove();
                  });
                }
                else{
                  ref.update({count:count+1});
                }
              });
              return snap.ref.update({text:data.text,uid:data.uid,timestamp:new Date().getTime()});
        });
