package com.example.user.ownread.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.user.ownread.R;
import com.example.user.ownread.view.MyObservableScrollView;
import com.example.user.ownread.view.OnScrollChangedCallback;

public class ArticleActivity extends Activity implements OnScrollChangedCallback {


    private static final String TAG = "ArticleActivity";
    private RelativeLayout mRl;
    private TextView mTv;
    private MyObservableScrollView mSv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        mRl = (RelativeLayout) findViewById(R.id.article_top_rl);
        mTv = (TextView) findViewById(R.id.article_tv);
        mSv = (MyObservableScrollView) findViewById(R.id.article_sv);
        mTv.setText(Html.fromHtml("<p>中国人向来有点自大。──只可惜没有“个人的自大”，都是“合群的爱国的自大”。这便是文化竞争失败之后，不能再见振拔改进的原因。</p><p>“个人的自大”，就是独异，是对庸众宣战。除精神病学上的夸大狂外，这种自大的人，大抵有几分天才，──照 Nordau 等说，也可说就是几分狂气，他们必定自己觉得思想见识高出庸众之上，又为庸众所不懂，所以愤世疾俗，渐渐变成厌世家，或“国民之敌”。但一切新思想，多从他们出来，政治上宗教上道德上的改革，也从他们发端。所以多有这“个人的自大”的国民，真是多福气！多幸运！</p><p>“合群的自大”，“爱国的自大”，是党同伐异，是对少数的天才宣战；──至于对别国文明宣战，却尚在其次。他们自己毫无特别才能，可以夸示于人，所以把这国拿来做个影子；他们把国里的习惯制度抬得很高，赞美的了不得；他们的国粹，既然这样有荣光，他们自然也有荣光了！倘若遇见攻击，他们也不必自去应战，因为这种蹲在影子里张目摇舌的人，数目极多，只须用 mob 的长技，一阵乱噪，便可制胜。</p><p>胜了，我是一群中的人，自然也胜了；若败了时，一群中有许多人，未必是我受亏：大凡聚众滋事时，多具这种心理，也就是他们的心理。他们举动，看似猛烈，其实却很卑怯。至于所生结果，则复古，尊王，扶清灭洋等等，已领教得多了。</p><block>所以多有这“合群的爱国的自大”的国民，真是可哀，真是不幸！</block><p><img src=\\\"http://img.owspace.com/Public/uploads/Editor/2016-07-22/1469180018875299.jpg\\\" width=\\\"750\\\" height=\\\"671\\\" /></p><p>不幸中国偏只多这一种自大：古人所作所说的事，没一件不好，遵行还怕不及，怎敢说到改革？这种爱国的自大家的意见，虽各派略有不同，根柢总是一致，计算起来，可分作下列五种：</p><p>甲云：“中国地大物博，开化最早；道德天下第一。”这是完全自负。</p><p>乙云：“外国物质文明虽高，中国精神文明更好。”</p><p>丙云：“外国的东西，中国都已有过；某种科学，即某子所说的云云”，这两种都是“古今中外派”的支流；依据张之洞的格言，以“中学为体西学为用”的人物。</p><p>丁云：“外国也有叫化子，──（或云）也有草舍，──娼妓，──臭虫。”这是消极的反抗。</p><p>戊云：“中国便是野蛮的好。”又云：“你说中国思想昏乱，那正是我民族所造成的事业的结晶。从祖先昏乱起，直要昏乱到子孙；从过去昏乱起，直要昏乱到未来。……（我们是四万万人，）你能把我们灭绝么？”这比“丁”更进一层，不去拖人下水，反以自己的丑恶骄人；至于口气的强硬，却很有《水浒传》中牛二的态度。</p><p>五种之中，甲乙丙丁的话，虽然已很荒谬，但同戊比较，尚觉情有可原，因为他们还有一点好胜心存在。譬如衰败人家的子弟，看见别家兴旺，多说大话，摆出大家架子；或寻求人家一点破绽，聊给自己解嘲。</p><block>这虽然极是可笑，但比那一种掉了鼻子，还说是祖传老病，夸示于众的人，总要算略高一步了。</block><p><img src=\\\"http://img.owspace.com/Public/uploads/Editor/2016-07-22/1469180106375531.jpg\\\" width=\\\"673\\\" height=\\\"399\\\" /></p><p>戊派的爱国论最晚出，我听了也最寒心；这不但因其居心可怕，实因他所说的更为实在的缘故。昏乱的祖先，养出昏乱的子孙，正是遗传的定理。民族根性造成之后，无论好坏，改变都不容易的。法国 G.Le Bon 著《民族进化的心理》中，说及此事道（原文已忘，今但举其大意）──“我们一举一动，虽似自主，其实多受死鬼的牵制。将我们一代的人，和先前几百代的鬼比较起来，数目上就万不能敌了。”</p><p>我们几百代的祖先里面，昏乱的人，定然不少：有讲道学的儒生，也有讲阴阳五行的道士，有静坐炼丹的仙人，也有打脸打把子的戏子。所以我们现在虽想好好做“人”，难保血管里的昏乱分子不来作怪，我们也不由自主，一变而为研究丹田脸谱的人物：这真是大可寒心的事。但我总希望这昏乱思想遗传的祸害，不至于有梅毒那样猛烈，竟至百无一免。即使同梅毒一样，现在发明了六百零六，肉体上的病，既可医治；</p><block>我希望也有一种七百零七的药，可以医治思想上的病。</block><p><img src=\\\"http://img.owspace.com/Public/uploads/Editor/2016-07-22/1469180141372775.jpg\\\" width=\\\"673\\\" height=\\\"695\\\" /></p><p>这药原来也已发明，就是“科学”一味。只希望那班精神上掉了鼻子的朋友，不要又打着“祖传老病”的旗号来反对吃药，中国的昏乱病，便也总有全愈的一天。祖先的势力虽大，但如从现代起，立意改变：扫除了昏乱的心思，和助成昏乱的物事（儒道两派的文书），再用了对症的药，即使不能立刻奏效，也可把那病毒略略羼淡。如此几代之后待我们成了祖先的时候，就可以分得昏乱祖先的若干势力，那时便有转机，Le Bon 所说的事，也不足怕了。</p><p>以上是我对于“不长进的民族”的疗救方法；至于“灭绝”一条，那是全不成话，可不必说。“灭绝”这两个可怕的字，岂是我们人类应说的？只有张献忠这等人曾有如此主张，至今为人类唾骂；而且于实际上发生出什么效验呢？但我有一句话，要劝戊派诸公。“灭绝”这句话，只能吓人，却不能吓倒自然。他是毫无情面：他看见有自向灭绝这条路走的民族，便请他们灭绝，毫不客气。我们自己想活，也希望别人都活；不忍说他人的灭绝，又怕他们自己走到灭绝的路上，把我们带累了也灭绝，所以在此着急。倘使不改现状，反能兴旺，能得真实自由的幸福生活，那就是做野蛮也很好。──但可有人敢答应说“是”么？</p><p><img src=\\\"http://img.owspace.com/Public/uploads/Picture/2016-07-22/5791f22543274.jpg\\\" img_dark=\\\"http://img.owspace.com/Public/uploads/Picture/2016-07-22/5791f22996120.jpg\\\" width=\\\"1153\\\" height=\\\"567\\\" /></p><p>本文选自鲁迅自编文集《热风》第三十八篇，经译林出版社授权发布</p>"));

        mSv.setOnScrollChangedCallback(this);
    }


    @Override
    public void onScroll(int l, int t) {
        float newAlpha = (float) t / 500;
        mRl.setAlpha(newAlpha);
        mRl.setBackgroundColor(Color.BLACK);
    }
}