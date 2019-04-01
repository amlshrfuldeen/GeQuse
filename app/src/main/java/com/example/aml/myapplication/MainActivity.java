package com.example.aml.myapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

//كلاس المين اكتفتي يرث من كلاس الاب اكتفتي الي فيه كل الكلاسات تبع كل العناصر
public class MainActivity extends AppCompatActivity {
    //لكل عنصر في ال layout يوجد له كلاس في الاب اكتفتي الي ورثنا منه باسمه وهنا يتم انشاء كائنات من هذه الكلاسات او من اي كلاس بنيته انا
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private TextView mQuestionTextView;

    private static final String TAG = "MainActivity";
    //متغير ثابت يحتفظ برقم موقع السؤال عشان لما اقلب الشاشه مايرجع من اول سؤال
    private static final String KEY_INDEX = "index";

    //مصفوفه كائنات من كلاس Question
    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };
    private int mCurrentIndex = 0;
  private int num=0;

    /*
    هنا داخل دالة الاون كرييت التي يتم تنفيذها اول مايتم إنشاء الاكتفتي او اول ما يتم
    انشاء التطبيق يتم الربط بين هذه العناصر معا الكائنات
    الي انشئناها فوق لكل واحد منهن وكتابة وضيفة كل كائن داخل ال listener
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        //داله الست كونتنيو هي الي تعمل انفليت لعناصر الي في الاكس ام ال وتحطها في الشاشه بعد الانفليت يصبح كل عنصر جاهز للربط عن ىطريق رقم
        setContentView(R.layout.activity_main);

        //هنا ياخذ قيمه الاندكس من دالة السيف انستانس
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            num=savedInstanceState.getInt( "num",0 );
        }
        //نقوله الكائن الي تم تعريفه يساوي التكست فيو
        // ثم يروح يجلب  التكست فيو في ملف R  الذي حقة الرقم رسلناه لها في البرامترات بواسطة دالة findviewbyid

       mQuestionTextView = (TextView)/* الراجع من هذه الدالة هو فيو بينما الكائن هو عباره عن كلاس لذلك عملنا casting اي تحويل للقيمه الراجعن من فيو الى كلاس عن طريق كتابة تكست فيو داخل حاصرتين */ findViewById(R.id.question_text);

        //يستدعي دالة القت تكست من الكائن كوسشن بنك المعرف من كلاس الكوسشن عشان يجلب رقم السؤال من الموقع 0 لاداخل متغير من نوع رقم
        int question = mQuestionBank[mCurrentIndex].getTextResId();
       // الدالة ست تكست تقوم بطباعة النص الي داخل  question الى العنصر في الشاشة
        mQuestionTextView.setText(question);

        mTrueButton = (Button) findViewById(R.id.true_button);
        //ماوضيفة ال listener? الزر هو كائن يقوم بالرد على حدث معين مثل عند الضغط عليه لذلك سمي باللسنر وعند الضغط علية يقوم بتنفيذ اللسنر
        mTrueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                /* هذه الدالة تقوم بعرض رساله سريعه على الشاشه ثم تختفي */
//                Toast.makeText(MainActivity.this,
//                        R.string.correct_toast/* اما نحط النص الي نشتيه مباشره او نحط النص في ملف الاسترنق ونعطيه الامتداد مع اسم النص*/,
//                        Toast.LENGTH_SHORT ).show();
                checkAnswer( true );
            }
        });


        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAnswer( false );
            }
        });

        mQuestionTextView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCurrentIndex<mQuestionBank.length) {
                    //يزيد العداد ثم يجعلة يعرض السؤال الي في الموقع المحدد
                    mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                    // int question = mQuestionBank[mCurrentIndex].getTextResId();
                    // mQuestionTextView.setText(question);
                    updateQuestion();
                }
                else  Toast.makeText(MainActivity.this,"This is the last Question", LENGTH_SHORT)
                        .show();
            }
        } );

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentIndex<mQuestionBank.length) {
                //يزيد العداد ثم يجعلة يعرض السؤال الي في الموقع المحدد
               mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
               // int question = mQuestionBank[mCurrentIndex].getTextResId();
               // mQuestionTextView.setText(question);
                updateQuestion();
            }
                else  Toast.makeText(MainActivity.this,"This is the last Question", LENGTH_SHORT)
                        .show();
        }

        });


        mPrevButton = (Button) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentIndex>0) {
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                    updateQuestion();
                }
                else  Toast.makeText(MainActivity.this,"This is the first Question", LENGTH_SHORT)
                        .show();
            }
        });

    }

     //قبل مايظهر التطبيق ويكون مرئي للمستخدم
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    //لما يكون التطبيق مفتوح ومرئي ويتم التفاعل معه
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    //لما ارجع لسطح المكتب والتطبيق مازال شغال في الخلفيه تشتغل هذي الداله وداله اون ستوب
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    //هذي الدالة الي رح تمرر رقم الاندكس للسؤال الحالي عندما اقلب الشاشه عشان مايرجع من اول سوال
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //log.i هذي تجيب معلومات في شاشه ال logcat
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putInt( "num",num );
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //log.d تعرض رساله في ال لوق كات  انا كتبتهاا
        //log.e هذي تعرض رسالة الخطاء
        //log.w تعرض تحذير
        //log.i تعرض معلومات
        Log.d(TAG, "onDestroy() called");
    }


    private void updateQuestion() {
        //هذا النوع من LOG.d يوريني اين تم استدعاء هذه الداله لمعرفه مصدر الخطاء
       // Log.d( TAG,"Updating question text" ,new Exception(  ));

        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }

        Toast t=new Toast(this );
        t.setGravity ( Gravity.CENTER, 0,  0) ;
                t.makeText(this, messageResId, t.getGravity()).show();

    }
}






/*
1:  toast.setGravity (Gravity.TOP|Gravity.LEFT, right 0, buttom 0):

2:صفحة 46 و 47 كيف انفذ في جوالي ويضهر داخل الابتوب؟

   2: اضيف الصور من هذا الرابط لاداخل الدراوبل من صفحه 48
www.bignerdranch.com/solutions/AndroidProgramming3e.zip
Download this file and open the 02_MVC/GeoQuiz/app/src/main/res directory. Within this directory,
locate the drawable-hdpi, drawable-mdpi, drawable-xhdpi, and drawable-xxhdpi directories.



2: في زر التالي المفروض لمن يوصل لاخر سؤال يعرض رساله ان قده اخر سؤال لكن العداد مابيوقف عند اخر اندكس بيتجدد من جديد كيف ؟

في الوحده الثالثه كيف يحافظ على قيمه في حاله حدث تدوير للشاشه:
1: ايش الفرق بين الفريم لاي اوت و اللاينر لاي اوت  صفحه  66 هل الفريم لاتوجد له خاصيه ال gravity التي تجعل كل ابناء الفريم بهذا القرافتي بينما الاينر لاي اوت توجد ولاحاجه لعمل لاي اوت قرافتي لكل عنصر داخله علا حده ؟
2: Bundle  كيف اطبقها على البيت بوكس
3: ترجمة اخر موضوع متعلق بالباندل وسط صفحة   70 وكمان 71
هو  كائن  فيها كاي وقيمه ترتسل من داله سيف اونستانس لداله اون كرييت واوصل للقيمه من داخل داله اون كريين وارسل لها الكاي والقيمه الافتراضيه للمتغير هذا في حال لم يتم اسناد قيمه له
 4: مافائده انو ادخل باعدادات التلفون اسوي تفعيل لخاصيه عدم الاحتفاظ بالانشطة؟


 الوحده الرابعه كيف يكتشف الاخطاء عن طريق اللوق كات
    1:يبحث عن FATAL EXCEPTION ويختار من القائمه  الايرور

 */
