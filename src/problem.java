import java.util.*;
public class Problem {
    static int  num=40;   // 微云数量

    // NET
    // 任务到达率lamda
    static double[] lamda={8.941963864020874, 9.8405728741512, 11.989136758084058, 16.867229113675908, 18.526091636107417, 10.655683276460596, 13.157699384237464, 17.23416780946963, 9.126598652335375, 21.204997597428758, 7.299540855211638, 9.548168529415074, 21.73079087099415, 8.08423056877001, 16.502661904985295, 7.1935858564144155, 13.137905694858945, 6.700109279645611, 12.350874254351908, 14.260217624564596, 14.938527414024895, 10.214036525223078, 9.647059553517048, 27.183943662641777, 20.180550520395585, 0.8463523651259806, 18.25826552665468, 16.168889624392296, 10.865588630985767, 1.7031674665511218, 4.988398180501523, 2.583265182649999, 7.908410086193408, 6.293035421453505, 12.19149481808229, 15.548869774441927, 18.729915351342164, 25.934645010416688, 11.535575878557745, 15.562388935442426};
    // 服务器数量n
    static int[] number={2, 4, 4, 4, 5, 6, 5, 6, 4, 5, 3, 2, 5, 3, 2, 5, 3, 2, 5, 5, 5, 3, 4, 6, 7, 6, 6, 8, 3, 4, 3, 1, 4, 1, 2, 5, 5, 5, 3, 3};
    // 服务速率mu
    static double[] mu={6.060160793593205, 2.7767881914082446, 5.153970240351251, 9.322305149326297, 5.223958689855033, 7.625944562358584, 6.993285796175555, 8.188892642091108, 3.9189121373632743, 7.013507874577343, 4.37197787773687, 5.779033451821048, 5.9782304773257495, 7.959697412560627, 9.40665557403188, 3.3245658779247367, 5.140669949794639, 6.862714625407805, 4.081948565734003, 6.154385147851699, 3.6854266219656053, 4.853440368748271, 3.4595396134285386, 6.545371695975286, 3.72533579037776, 6.134791525803253, 4.901661104694927, 3.3491820005522897, 6.826568056300988, 2.8078931515436985, 3.2003219694778426, 6.235539418509374, 7.6502793597768175, 8.162960171593696, 6.6702151173645365, 6.991216170455506, 5.697178927365415, 5.767429421890271, 8.169394571184169, 6.688052986885431};

    // 网络延迟，netDelay[i][j]代表微云i到微云j的网络延迟
    static double[][] netDelay={
            {0.18424757740553566,0.10751037187814919,0.17666453114206035,0.19138000383211898,0.10907239999586688,0.12512728654309865,0.16306447949191286,0.14625593354199853,0.10239110304589144,0.16919274177694288,0.1557406846633339,0.17698368359113723,0.1521016078927683,0.13637602039773775,0.1589472568613674,0.11902204239418594,0.1237066645809959,0.11961265141523533,0.11952646499305948,0.1066702994213761,0.15289134336705879,0.17174673471621432,0.19142988678775508,0.18480317435998445,0.14646312748194532,0.1477413866675378,0.1448472672427867,0.15925385875978965,0.1175912946331903,0.16970303117464414,0.1668615970672653,0.15023695674726198,0.1938630318324186,0.18494996307274894,0.1495458311775379,0.13033616022151315,0.15429846666554697,0.12215486028055368,0.11897738918908116,0.18650892087973592},
            {0.10448356809577017,0.10671115092974282,0.18483222230478746,0.1445341839193576,0.11987554620494856,0.1640795169348546,0.15825928748958246,0.17792833622544826,0.13441086454047985,0.17193393261889017,0.12328353830713834,0.16379298206891202,0.11668415825352763,0.12743237293722118,0.12507871810067256,0.17740953278012284,0.18852602080585976,0.1537239187195876,0.1294905985712424,0.16021309725248187,0.18936972996659843,0.1401777629950257,0.13982049568427773,0.1666094085259777,0.15384936127769985,0.14210004079756586,0.18332523625686764,0.13155567686202332,0.1899492367272496,0.1376398671823125,0.1597625033318403,0.17886884297753075,0.12393968327186278,0.11266234704705147,0.10543918827526305,0.1446078044185894,0.17760896210229923,0.12446507091840311,0.10873311391815929,0.19767250343520087},
            {0.10299806634235212,0.1690792307479217,0.10164853686228299,0.18537504024803841,0.14535357077308875,0.13250578708633698,0.17001114403653192,0.13804082282549457,0.15431861625261173,0.14172770566597143,0.16344347695714112,0.17621765784239415,0.18991439762676046,0.15069194777918613,0.14898760402062441,0.10572374747071728,0.1230466749662799,0.18938302519129846,0.15838617881273953,0.1623032496742004,0.15469984950420643,0.17140503120875983,0.14365826083908137,0.11652393745378278,0.17678663964232183,0.15959506655424763,0.10477702759109254,0.10134819652456933,0.13637715661826896,0.15899025708094122,0.12583301108966016,0.10543492611409339,0.15657593939547548,0.1693928160619093,0.10715858737882245,0.1696313017316013,0.12117592530678487,0.15118353332429493,0.12402213339470163,0.11293270906437783},
            {0.1023046945584549,0.10461237632744033,0.1718335149746317,0.10652673692887697,0.12487875047607994,0.11435867389161873,0.17916277607410788,0.14687957521471656,0.15414185334982886,0.16735942649962596,0.10452821456764408,0.1245995692756923,0.17850675540761002,0.15202127752152614,0.1073428163221381,0.11307655654946963,0.11960540270165859,0.1980423303622817,0.12070750614259612,0.16282736141127385,0.19964775574351012,0.13071185758241194,0.15415096202256937,0.16661609944050365,0.13596479899783417,0.1274769512184196,0.1489167749488288,0.10109675114468607,0.12539746174776084,0.13996839511821457,0.17761149306506338,0.19982438955242626,0.12794259107839492,0.1948263571375558,0.144703216106729,0.1590737578529898,0.12320758773716763,0.1445189948388393,0.15421215096053967,0.14861522897642993},
            {0.1843241206995419,0.1796755194266429,0.15160461224288965,0.19761843516366595,0.1359572654902599,0.15053520798480988,0.1770093097677995,0.1031127865268675,0.14560837328018303,0.18250895644950302,0.1341204684336087,0.14347956878130402,0.1463171310753031,0.12504541805219477,0.13844245780126807,0.16613552454019365,0.1804626132451773,0.1821499424182984,0.17627585041985272,0.10980287465911309,0.13602191777679912,0.16044538503342543,0.1992111103647848,0.1637194657482144,0.12722639471289973,0.12637231571917984,0.19300900947525834,0.12283176793293935,0.13479424303368331,0.1796599392547827,0.1266096320126066,0.12416650335394948,0.14550875599091737,0.11001030861234232,0.15398825171240088,0.17664052327602855,0.1969973143848649,0.181261096033778,0.15018559777927148,0.17541447415129452},
            {0.17377535288997487,0.1230370758889,0.16370162431419566,0.12779909029075642,0.143640299959827,0.15956745401749803,0.14132698915770092,0.17711951300681203,0.12023245612422896,0.18577848508982206,0.14562520440998591,0.15266249428508308,0.1541522684296854,0.13077879490228436,0.1402562829095702,0.14247991453528377,0.15795170541379835,0.16275519079797385,0.1909275517971114,0.17311250988014804,0.10300944482145591,0.134838782802755,0.11808147417416986,0.1504691645953031,0.17260652251189096,0.12147165388569371,0.10271547193821368,0.1487656612147553,0.1480860307026471,0.1650331647714635,0.18932244962231026,0.18428665496699517,0.1733936947652289,0.17171003847897437,0.1701606549408673,0.10028529972950143,0.14867589669784498,0.10555513044469736,0.14411094421196172,0.1420776218133104},
            {0.11799186423385465,0.1551651865924942,0.13756893040471546,0.1516857902324767,0.16346657841219536,0.12854797176436333,0.12223061257358837,0.15610502192833897,0.16332872523878394,0.15946735481655025,0.16190056598480743,0.19049532809489955,0.11260117301216399,0.16247529666957333,0.19163757100233655,0.16699209494899775,0.1445147815914793,0.1900409704540955,0.11830931746870163,0.11110783296235677,0.15510788555071064,0.15387555134416822,0.16435403736694149,0.17592845265809842,0.12543505452087222,0.1089024930677494,0.17795634432956062,0.19484909361812797,0.17822983654404057,0.14847937539330902,0.12235367727728877,0.11097756493254878,0.15413392503904205,0.14930497329357714,0.12989028176551448,0.17444948393783616,0.19609568156743648,0.1673297412460271,0.12890474016818979,0.185850769416591},
            {0.19652208464561335,0.16998573161943495,0.11061905274559514,0.1981631502247846,0.16154035362417252,0.1350723755935984,0.1612919956418887,0.17453447924444462,0.18295014067511386,0.17301029053871006,0.14553759130953264,0.11034810461914815,0.1726486163895018,0.1589942012520037,0.19752841931986564,0.16948595033176098,0.13763642887654987,0.14148384530628677,0.16501516890906956,0.16102754497964197,0.1191540457843803,0.13111271203999403,0.19621470900568375,0.17493278602431753,0.1540680999787498,0.16283979378645516,0.1536227294771853,0.12269056245336675,0.19014913018038718,0.17612728936513894,0.14876915106980676,0.10715070767093193,0.10167787497994574,0.13628321004582952,0.13368329203983348,0.13855343984949162,0.16904844328766325,0.1346108640439337,0.18384100741353077,0.15108831098602526},
            {0.12635517131236895,0.18791727980419595,0.1610128010711685,0.15193783855809945,0.19251559682145938,0.1420901382871417,0.1155787616662707,0.1991507321882268,0.18137147966903555,0.10028911518789443,0.1658634833237311,0.18955303811882165,0.1558103079341352,0.1872835942711945,0.1382239704206927,0.13527736011076918,0.13909976153216294,0.17947295036805944,0.16494779693295197,0.13080762796060758,0.16396281640179994,0.15156158452953813,0.11318278444760246,0.19278212288062666,0.10011198788011758,0.11870439494353024,0.15019602847283586,0.1632853758363525,0.1694270524111795,0.15289277862753534,0.192435098568739,0.12974126947027018,0.19724534010465977,0.15880214152319852,0.1729286199706733,0.13298572864826141,0.1612897581338679,0.16500129529926702,0.1067438851810799,0.11376495388641542},
            {0.10780010473992077,0.13191707491302831,0.12471133272363123,0.16431102587659396,0.1548000668398574,0.17026040957897298,0.16151605210544562,0.18220860633605135,0.11292636840590092,0.18004925188009954,0.1647735807561175,0.15908700354725508,0.19165231599250096,0.1580076134846645,0.1640980006523695,0.12110362947269408,0.1786148596006092,0.18997830519727482,0.13447218981766118,0.1611753977106815,0.18446714220145188,0.12244364687472806,0.16983482254369497,0.14847804081851176,0.1724700412605552,0.13675623771374948,0.144748110775611,0.14306859163896166,0.18395707288801172,0.16404515669302627,0.15181493392426998,0.16082648724894427,0.1540659379250968,0.13998140280369972,0.1460237143735897,0.15862123045340212,0.1546582378263961,0.11660934732303649,0.11744073006321656,0.1285765708489835},
            {0.11719918126378942,0.15654350487571722,0.1647232178695842,0.1621686608102676,0.1138040786128241,0.17147474794576367,0.18235130267597957,0.17673722661267777,0.13827872083480974,0.11688910438623672,0.1804996235528331,0.14404063075579165,0.1631856537073361,0.17415099819952434,0.16605022927305588,0.14268670013408563,0.11498662856871408,0.12397645446006395,0.1352072601450583,0.1786766476088473,0.15678716032463286,0.19906593196853814,0.16893075881104982,0.19378865423723682,0.13479346710141688,0.16731573141950687,0.17965518916755133,0.18372157215882967,0.16798955724145725,0.1703333284588704,0.19243666648754032,0.12375513350367286,0.17042395189215856,0.1098146866738432,0.1599370506684836,0.1511247265409338,0.1492175126449534,0.15531804932471097,0.1977866077048216,0.1394675252009054},
            {0.14087152621470717,0.11833195509260669,0.12342120267416357,0.1987958429839941,0.19709227024744932,0.15619824880614985,0.14783557764764046,0.1343837084017943,0.14663591622204708,0.12498717039217075,0.12852004503069817,0.15529779296626298,0.15657239517899565,0.13961314842656508,0.154448886052088,0.1260716878264135,0.1869379255057802,0.16536714978350822,0.12961994881342903,0.15178330206317217,0.1588062423905597,0.16431149785693888,0.13263155720625336,0.18283688468519196,0.10822774836839355,0.1797608350173473,0.11455549616515057,0.11926638951719616,0.1793787036862133,0.1932932723146172,0.10711425335178656,0.16056604822748255,0.1298552243062144,0.10113270911329231,0.16492172229279414,0.12124034093570482,0.15141659718199083,0.10730058153590488,0.16626384743600353,0.13454887501212218},
            {0.14462181555329318,0.15888119615307467,0.13561564988879243,0.1334356068070361,0.15600686048633153,0.17151844921306023,0.15392093429212275,0.19829581121662582,0.1165792231886263,0.12114332514959233,0.14055224412705072,0.14850874159383662,0.11586465824620767,0.19088920911913995,0.11079769356638855,0.12455686076260247,0.130848881776163,0.19115088328331997,0.1255679051947045,0.15084521197642847,0.1760722517606427,0.12683918435518035,0.1331757354356642,0.17414920435146108,0.1995222791418167,0.15585739823252107,0.10647091610149328,0.1809196620774913,0.15175508027793627,0.19253539564397762,0.12567163141959525,0.13372083441053798,0.10502595066585307,0.11807827505211524,0.1963613991979078,0.18255801849803183,0.16118499670881847,0.1611662707674732,0.13155887123870783,0.1454837714286206},
            {0.14591953395437715,0.14840034500951643,0.10009711948213504,0.12046955568309366,0.19762208094035988,0.19822944134717901,0.18910999527145067,0.17785844411065166,0.17998432521072077,0.1361908576694917,0.18167353912140016,0.16246642498105815,0.13940895167580672,0.175137973410169,0.18209999102906138,0.1304167125534551,0.10892169453060312,0.14131541504369322,0.10389004608698804,0.1924772147912003,0.11748798922270341,0.1411535870753941,0.153454812898541,0.16551373698543378,0.15050854059445726,0.128669183764651,0.1353770250059806,0.10753111828967218,0.16364602225486397,0.1342917547749966,0.1352643379841026,0.11139489622720122,0.11611427158299877,0.12702640578976013,0.10872219780891466,0.16186769827295477,0.12056125116102742,0.1300079375514307,0.14438445958163768,0.19917679223000184},
            {0.12906071502540065,0.18779569043985536,0.11828489088662106,0.1043866563562259,0.10560697413138403,0.179540646928209,0.18427555407686544,0.1385566813888275,0.11989107239975992,0.10903918733755068,0.1508116693908685,0.1568929015487899,0.10234169385974504,0.1060893217347321,0.196463820287665,0.12250574564185768,0.13934289690902715,0.1598406613172609,0.18984426319949663,0.18491425380624532,0.1786110829275063,0.1345369026208739,0.13319401621863614,0.1704430550881746,0.13424780165324465,0.15036682650247998,0.14155638345330157,0.12116422803644418,0.10922429736095204,0.11360005186395629,0.10193615837885994,0.14611425291715913,0.17239592041110155,0.1380976624181888,0.18463193030848196,0.10376569903727426,0.19851812044691625,0.10965802277119491,0.1506231297063477,0.18661637473197495},
            {0.15607778303898281,0.14390990204187318,0.11906736613039245,0.13721406941545503,0.17744638362580967,0.11789780235940944,0.15483860760863474,0.12216964260088359,0.18321707210145716,0.11189494536063505,0.12307034328227417,0.1725386136901254,0.13904768568339604,0.11589195448675585,0.15356802932576713,0.1943882244580323,0.1755801885167991,0.16591045506917942,0.12566464779578976,0.19152944760834398,0.1662429421437233,0.12770455789681742,0.19108533220806764,0.18774849949210476,0.14156817289128407,0.1881474902757652,0.13627647240157856,0.16850042779362667,0.14813144041578274,0.19464815623467233,0.10638639357021042,0.13504173499501856,0.122484986869699,0.1564743133362827,0.1699582120603587,0.14921823888303984,0.11774827503241511,0.152044966449879,0.19253741996271043,0.14982044344415335},
            {0.1780426422853184,0.14731868299923828,0.12413754862949544,0.19029565352940211,0.11194372891276816,0.1527933815797579,0.16607640614620622,0.1528308707973506,0.1818270184002096,0.16966731024311532,0.1843521853916979,0.1098261108013692,0.10479598258997848,0.19115788738481437,0.12410223821300614,0.1784001027923835,0.13405330073120758,0.16383508832664356,0.1463562484368491,0.19026499793949656,0.12094917044988802,0.11717124828401966,0.13454934464588122,0.11445545938594266,0.17436379422747947,0.14187729489592416,0.1470107750418488,0.1289272137263113,0.12058651313382308,0.116554105348628,0.17902928230469595,0.19538982804371874,0.1948030286257321,0.18081327259907104,0.11457943310736718,0.15481286380945425,0.16513638136435863,0.13817213780922522,0.10744632725259673,0.11695255721556222},
            {0.184830543586347,0.16651066022263103,0.10194302077133677,0.13931031771171715,0.18730287177243693,0.18261537552968626,0.1480906983957833,0.14480620432358116,0.1765232083874937,0.18120566001045552,0.14624251500326016,0.13326075699250664,0.177808468738349,0.11451358290463015,0.12541491439827893,0.1436104423020015,0.15613787687538766,0.13592979202309144,0.13474605659452224,0.11088928851036167,0.15071739732347844,0.1539958911257246,0.17615970936506087,0.10586613743311388,0.14126838814691234,0.14518020445369087,0.12467665307322447,0.18718822442544591,0.17914547743869547,0.12995624073659587,0.12706417267342865,0.16415105609066302,0.19118872208064067,0.17895607665484828,0.16734579698204047,0.117817793490856,0.12410405653972693,0.1853118809901257,0.13677083486961963,0.11786860477946418},
            {0.12635393271820175,0.17605022041730503,0.11114319152524996,0.1712103967083903,0.18652609746905455,0.17890636771790613,0.1737594211057755,0.16569165131179592,0.11492977971692911,0.1661354745345962,0.16415616817328674,0.1774447418952574,0.1697822914869767,0.19805013725426718,0.1204342514855607,0.13030689145612057,0.14349416134321646,0.12475659139368715,0.10406506642301126,0.18438938507281413,0.15191470998807555,0.11895358953982325,0.19783018501306016,0.15157838748123026,0.11882245939089282,0.12063904362863781,0.19498233157224643,0.11900361618266272,0.16673385430606918,0.10927915311593495,0.10212922905571262,0.19306464863685244,0.12635493389955899,0.14424896271767573,0.13681897452075736,0.144963678986742,0.13442100129871776,0.15753443159321787,0.12005837749990364,0.1309385952487725},
            {0.15860192571813178,0.15463267532441138,0.13647426935066254,0.1659524733334907,0.16896364600908984,0.15033890112619358,0.11687298216817507,0.15689412041351414,0.1480379770671099,0.13411000313428478,0.15278822932656788,0.19380219973826115,0.16574526310396476,0.15458107907367238,0.1211244981411362,0.1649400869845174,0.1516591938755116,0.11525752740105312,0.1708028172348975,0.1279302582539492,0.192253909807936,0.14448922361433644,0.15655691343294284,0.11135603338196699,0.12051626886101205,0.12186596520261435,0.1390113297000079,0.15167099791776223,0.16506987565823214,0.19557365975317942,0.11108243979241424,0.12535800146816908,0.11464481341053528,0.17745257374330026,0.15693687960324396,0.1088386434675643,0.1620630996499896,0.15737037193089287,0.13426822819510828,0.11266177741465022},
            {0.19595192118568067,0.1302010428368255,0.13015051545361137,0.1203685489791135,0.15423451847175412,0.18582378203231054,0.1268165289312793,0.11575976020811607,0.16757214319918412,0.19356538280793667,0.14504255937287108,0.1650711487014309,0.16201382038437082,0.14171841405246244,0.13391460886342002,0.16162548229268178,0.16155410924487812,0.16699848284042373,0.12904704951991666,0.18823283962092954,0.1593319147511759,0.15647127135521782,0.13818064145943806,0.12601517918621732,0.10739696384949299,0.1281082350671789,0.1841210853886327,0.11315589719608476,0.13851213256712383,0.12846309132955117,0.18758808542575517,0.1831533692108267,0.15632432022615994,0.14800926159087477,0.17905021385030026,0.13405954642282297,0.1735122705632952,0.1932517420377072,0.15315559257465983,0.17215392173225147},
            {0.1774455321025201,0.14887942206632995,0.16154535477852855,0.13341497520155604,0.13410426490928054,0.17331789042779486,0.19782260932403642,0.17496074079267152,0.10503561140946438,0.19011822529304853,0.12274957212690767,0.17434092511044852,0.11650438794548784,0.14189733617468034,0.18290973338609742,0.13010348565303925,0.14619515005895162,0.12124775857651816,0.1507721667096272,0.16445695880945546,0.17969282684695145,0.18793575229089127,0.11582049921750748,0.19157900891113902,0.17079611311075474,0.15549969396813554,0.1296441358233195,0.14771360549425347,0.16801569897274457,0.1907963156783521,0.11693778508430286,0.1350332917828664,0.17244204480035558,0.12663266679734078,0.10299876085798756,0.1403696380222449,0.1704386379674861,0.10585832285226351,0.13628740502915995,0.1767058703507144},
            {0.14938939915542834,0.1431496236950393,0.15163448181254577,0.1318682706243262,0.15291932907642103,0.15234098749442368,0.14926226821182872,0.14939181643621233,0.1826566865808308,0.17146364633236444,0.14005874783555547,0.10029445301482781,0.1917419198025307,0.144171279468665,0.10076656039903292,0.18174818103901375,0.19921941766756207,0.11205366485053411,0.14927312062368328,0.17935790988036818,0.1996962129902905,0.18241625081219254,0.1477051409524115,0.18845074425175057,0.13942087422984836,0.1730923843139677,0.18688261915340812,0.16410452681092574,0.15968237074363137,0.1354848283542414,0.10982061286612571,0.145864762599025,0.1828807217979552,0.10261711233682344,0.11975604522445388,0.18026930470849695,0.16901828246623662,0.11557247771879273,0.13692571260755027,0.13083863943170498},
            {0.15643804186546456,0.13537933694935667,0.13604944680107672,0.11032745283648601,0.10267914646383212,0.15461748465161498,0.17376923821453433,0.14895228380985456,0.1611656266687835,0.11314522189685308,0.1671438728831443,0.13960205587317615,0.13347740979564202,0.17743509839984223,0.10403236251557818,0.13505897493747832,0.14904587481931839,0.12340674477034573,0.1754554651386123,0.16278950765644637,0.14993074752883034,0.15064729078402464,0.16547626921943803,0.14447807385906866,0.1660333300340455,0.10590367587445182,0.14561649322450648,0.15777199599448594,0.12586426994074068,0.16441224322221565,0.1440456772608019,0.13856808578539853,0.15756245692995932,0.1948337021093225,0.1393617081669641,0.15047435892086095,0.19845784270694125,0.10327601726217273,0.18359265088198165,0.14328300790186574},
            {0.13246635163906254,0.19814837603400007,0.1673539735844786,0.1962585701731309,0.12624026889670426,0.15336472551770444,0.10357089634484623,0.1488587039026472,0.11267545373387519,0.14379717307085949,0.14393772045938757,0.10383711601621295,0.17908959101511862,0.119363029322946,0.18213016318323244,0.13614370034424433,0.11068006852136064,0.12978728113266894,0.1436286371088548,0.19381988533124025,0.13223576989265012,0.12120505914846631,0.12704429061103933,0.1539973183920862,0.15711482728295326,0.1276378168647,0.18755071731840656,0.19439881689229593,0.13868702565518148,0.1581897774758981,0.15935167543484982,0.1294518631479988,0.13164589723286388,0.14136272789094464,0.15117788905041196,0.11671652168515413,0.14731718959302229,0.12106446003115666,0.17861950807932014,0.14885822969840248},
            {0.19627937932414302,0.18383217792019113,0.16248627455094697,0.12794708671341293,0.14484067433739625,0.12068543307811419,0.18503739689118476,0.1581507962093868,0.1346444269645286,0.11517251495140711,0.14330151981342218,0.17960317635956474,0.1464387844053291,0.14788465744691673,0.17543816915931867,0.16829789762574895,0.14024011007695647,0.1326576248839264,0.17950430770708517,0.11218457310071403,0.16910485540567127,0.11745889589618386,0.19755648401981507,0.12239654521820198,0.15320873820859035,0.10773991096447198,0.17920695712239337,0.14464568691888252,0.1356714928066757,0.13280751270123214,0.1926951574178098,0.14451174316161267,0.15835522962013174,0.12720684435981985,0.1444176328173792,0.18176591318488466,0.1144927382551524,0.19885978396229453,0.15487040650869427,0.16561453380761276},
            {0.1734043825026135,0.19363217782333075,0.17224287907040756,0.18102785632366053,0.15831505217184835,0.10976503552085595,0.12306856938079185,0.146793080067004,0.10452533612613177,0.13029803389841527,0.15466118693372005,0.1846553469805039,0.1616366685830675,0.17258315389733228,0.18815945065965056,0.11080781285514427,0.16797921994749465,0.1356986918993477,0.1924433716359915,0.15559722433215475,0.19076312621201183,0.11685623327589315,0.18835164541695784,0.16594786682691967,0.15032637906378504,0.16935057572770454,0.19416223388143422,0.10534994390762047,0.18591237047344486,0.10617830515692173,0.11858924427130325,0.13790704689865857,0.13584260798767095,0.1910782572997146,0.10369707004911627,0.17120213844042112,0.19829203962641284,0.15051510926829023,0.1160128312768314,0.17955521851675083},
            {0.18506841235176844,0.11405822598348753,0.13477565316451406,0.1405117940260196,0.11721740504631611,0.10489026621639039,0.14635353263506862,0.14808943907990976,0.18129456351409456,0.11976210461631592,0.1206186435181679,0.15635921300187317,0.12979493798508454,0.137518565706998,0.10481321637343953,0.15776909873581632,0.19560777479199395,0.18822508260282783,0.1367582013676779,0.10365023315133193,0.12676492686965773,0.1442418622334167,0.13512252709418465,0.19550867346284925,0.13291061622366826,0.18442053125164856,0.13985607567247368,0.1787719424712671,0.1755107626446806,0.11056741646703981,0.11760350191865396,0.12131655886798486,0.1430103598966623,0.1867396915417143,0.16264511113093727,0.12691908772478305,0.12027730261064279,0.12784411090561096,0.1026591201418571,0.13614699318126067},
            {0.10857759121195831,0.11787261566544768,0.17729319562763501,0.1673012954768449,0.16714934991505712,0.18708985461253413,0.16825397801080122,0.16323503065812814,0.17591859802761367,0.19613632173357698,0.13871987282590575,0.16314614117800472,0.13214810813509426,0.17871265473978942,0.17917859256366214,0.1214130751117451,0.17417396865318063,0.1461759973613256,0.1796877665854394,0.1508607311493794,0.1516251585392395,0.1491230829964662,0.18786655878644343,0.1555862983085523,0.1633304545401149,0.14065211088111101,0.1514649270800922,0.10936715734416314,0.18892603363248114,0.18262744388388916,0.19018261640052725,0.16553928263146608,0.17547293718363177,0.19998737717645673,0.17086935559030236,0.1467226593800915,0.12053925851121715,0.1476550925988654,0.14223358453485696,0.13700395172330654},
            {0.11681839846926087,0.13112182356914936,0.12629344126069209,0.1340468039042024,0.10732464514455872,0.16627715531350967,0.14532929450658158,0.1688508510059884,0.16809138858474243,0.12134914585556053,0.16534287105011905,0.1234279577100346,0.16599813710074587,0.1272722445048837,0.1367341009957657,0.15458552019161328,0.17206220020224036,0.19659914568192402,0.15281568472992904,0.12223664972911581,0.15107917913696156,0.17332250518668826,0.15667443675650083,0.11554928116454655,0.17572135388767057,0.19527183952045074,0.10464364330254479,0.13854236775413786,0.11918236984374718,0.1038645902696301,0.16818906281042909,0.14006979501992983,0.13890268459945151,0.10061560599393532,0.14759047316868826,0.18480722702324562,0.1460597971246037,0.14656437382023965,0.137417619044781,0.1608545981913939},
            {0.12586735219321396,0.18050757820019103,0.17533949449091732,0.1977834899167058,0.15346277449437898,0.14963620003579212,0.16564803247573098,0.15538199541301642,0.16208468792896766,0.1447442167890344,0.15737106209437232,0.18383056323555394,0.1451943944324262,0.17271013565636315,0.10324186628238605,0.1563413320434946,0.1592700428677271,0.1674869800143602,0.12192419369695363,0.11336034633825605,0.1955598363294837,0.10155145975887567,0.16013080550088535,0.10914223350932273,0.14642986997226917,0.17236554522273187,0.19951010129093644,0.15470703271137126,0.16362136212423772,0.19607851847205987,0.14793242015305172,0.14772618794203318,0.1536069713130363,0.16259349038902463,0.15749587434044496,0.19044030408538581,0.16187622123983503,0.11674896450475863,0.19714981339123813,0.19069317071616346},
            {0.14318835142287425,0.15184553156546754,0.18373097427914525,0.19785286413358558,0.13496228500170693,0.10618867832111872,0.16438750564998753,0.18434246100371698,0.19448017264290812,0.1894597211304184,0.16461460167801312,0.19316944861984578,0.14197615219084805,0.11073759871603898,0.17982211833473355,0.15948775887373873,0.16531850069110415,0.11464587464997464,0.1944801251521642,0.14993846901633692,0.1753541076581538,0.14823899777199206,0.18711624950097813,0.16307485396493887,0.11651354058993497,0.12342820563181278,0.1584443477490201,0.15654180566781853,0.15546803939731413,0.13309275574464288,0.16968725856729372,0.17299738117877453,0.1477198085591187,0.17700176300617848,0.11459816909238116,0.17364949314598518,0.1743564353026342,0.10750659309994642,0.10630662018486839,0.1595499594775765},
            {0.10654359929604579,0.16650849620409422,0.12452712303695317,0.18292002460235113,0.1363424540964161,0.1198073196553522,0.11545749263009193,0.10774137059168953,0.16544265671349268,0.10583207835001915,0.17918879758340944,0.10521662372069131,0.11837193767747126,0.14733670130487983,0.13284008261674296,0.10358728095196408,0.1946854891738514,0.11854130671448071,0.1044747868276438,0.1411984924641904,0.17506139329215567,0.15580123962792644,0.14945523079679868,0.1840304163537227,0.16472866733274594,0.11266240428114449,0.17463347025353743,0.11507174247481344,0.13123038190187797,0.15132112043530027,0.15745224172371575,0.17344285206947543,0.17136160381752336,0.15610453475068345,0.13948919214401867,0.17066915199486093,0.16394886973753456,0.1233667616052296,0.13473070687068073,0.1558488560222088},
            {0.1222612170062752,0.16180278136630719,0.10361391226312655,0.1794899611740717,0.1526451745444001,0.14766640322209196,0.15831132561644728,0.14403935973323043,0.18491914835604673,0.15097633637085936,0.1337426241162963,0.18712908039900597,0.1294878705229831,0.1220702612407669,0.12852856768132495,0.153221921474166,0.17157256180222583,0.1089449010286005,0.14039887138665252,0.10269920689962561,0.1457603666011615,0.10015451088804087,0.12869704295883633,0.1564496138334006,0.11748754413389738,0.11883169013779485,0.1488792599773674,0.1539392953206195,0.13159781635296675,0.18242177245171048,0.1860144147636071,0.11405389801212104,0.19703569633762524,0.18036026101497887,0.14050686730424458,0.15920350039375872,0.16144906096929246,0.17367435437942794,0.1398194349231743,0.16443327662178003},
            {0.12103410115647706,0.1411910753787256,0.1755304656848557,0.12094112292918854,0.13510142201167613,0.1732426986955759,0.1694979091385972,0.18373692055623864,0.12456048022124797,0.13278815831975802,0.12876244518614566,0.14564789986846383,0.17695765374873648,0.15324944017812023,0.13399772539803556,0.1768295590997039,0.1903371495734766,0.19994991960874828,0.19072151889272285,0.15885024608667686,0.15094363392074206,0.14147621491273285,0.13905950373458742,0.14168941101472068,0.10571121066591277,0.1205910765645629,0.15224139482993798,0.10310745122722403,0.13998913610486252,0.10000021475517631,0.16928122483378552,0.1467480111199561,0.18043144668485983,0.14558133613614324,0.1513854238149634,0.13790312992549383,0.19066697917987896,0.1636016601967644,0.17745614597606835,0.12893536279270185},
            {0.13243551295247202,0.1310695138495729,0.18769306223511795,0.15082790087177222,0.15723993451789456,0.17634496580862288,0.14338394335905474,0.19103939630303715,0.13278744182641936,0.19035194396014699,0.19755476728323293,0.1436814741800617,0.10065966089071193,0.12655448178152576,0.11880394080342466,0.18316038414227792,0.15913722243235615,0.15187249352341237,0.1885882357593199,0.13778931741292139,0.18984553434792828,0.14456383129810624,0.15501575192294667,0.1300282173837411,0.18373765831898944,0.10127434688999307,0.1288999522073252,0.12710501626471382,0.19535222987573442,0.19800132049185917,0.10767865079681263,0.13717157613130837,0.12313863728910113,0.13715069816998035,0.19566186075391317,0.15437995627633896,0.19613280291025917,0.1990344240303277,0.1554130476920915,0.14209783034311374},
            {0.12544698849753047,0.13397806327960654,0.1534388367396899,0.1716311005229603,0.17954690218499497,0.15792160872190156,0.10646859550530657,0.12187036156688417,0.166404537134209,0.16897429825078208,0.16472892793856203,0.15324149150365154,0.1000099059364736,0.13188911157845246,0.19035378895180535,0.1918271801962194,0.10839517117306957,0.13206794994480986,0.1985045016545351,0.17941398538173722,0.1048119015919588,0.1842458461771796,0.18992862978976363,0.17509033156449752,0.1607661061359194,0.17732149132654612,0.12392412160650598,0.16889557988699044,0.19971970405164624,0.17036054989091148,0.15296139751571758,0.13993820599734424,0.15890744028164336,0.11380641290369366,0.12384968879806083,0.17940730388447398,0.10098739551789021,0.15734209322304088,0.1455839318404173,0.13611512339018844},
            {0.15419926305406442,0.13800354624302585,0.16054392466994052,0.1524185709739177,0.10229791519103842,0.1787409258568633,0.1327891062713469,0.17720160169235968,0.15547211552715914,0.19978031617879982,0.15785983922397614,0.14947378675359346,0.1878795852599887,0.1257020325038817,0.1455725745589451,0.1319455776577423,0.19966661091504584,0.16765426656183377,0.13754199420339597,0.19117824199410358,0.12031854801614893,0.17589976476326039,0.11926131805474043,0.15138583297407507,0.10036093747860927,0.1730938050063629,0.14234155876293628,0.17535601988597047,0.10877604473538366,0.10419367582905437,0.15768495863418697,0.1509326011139736,0.1759162351103839,0.11977661997459557,0.1546840934686139,0.12250302228563996,0.18551070789068355,0.17699111011978877,0.1708105555235839,0.19526159826650233},
            {0.12696749738660756,0.11996650620030644,0.17531714586904631,0.10350700958123105,0.11993687812602297,0.17252254655757243,0.13611589319034473,0.16714267823382079,0.1726210990462233,0.16242761290951283,0.11196562579314628,0.13313840742345778,0.10144582745909439,0.18549769810061673,0.19026277015274926,0.17477819433413247,0.1833175777900038,0.12091825487380216,0.10458098368910214,0.1950668995658679,0.13948068472437933,0.11396439835254873,0.14346247070952903,0.1543538465334971,0.17791599618567552,0.11917503983355097,0.1747984978090011,0.1313043532837517,0.11182218147083356,0.1238431813433869,0.12418509797993994,0.10542143433084242,0.14655678946561565,0.15666212257407805,0.14056396541017052,0.14608718729201386,0.14255173372143357,0.11915522120520591,0.1758816725067363,0.16532169649589107},
            {0.10800418150363864,0.18492541025796738,0.18214542187004115,0.17221320010065222,0.15590790726649234,0.10359392187505961,0.12353216064223396,0.17692473946178489,0.14118324434459892,0.13062555862889122,0.14671382700756064,0.19451508465609124,0.1206043354758837,0.1237777353433378,0.10548245623607974,0.12714565040997566,0.13366269121401167,0.11417763556438768,0.10633101777833043,0.12418103880497508,0.17370595262631977,0.18088221778565589,0.11919797235816663,0.13510362883887267,0.12250210341155181,0.14896208716338505,0.1573679384723361,0.15364878452554456,0.12204563093389587,0.14668479709683155,0.1136664483324317,0.12003311644133906,0.18311317892328965,0.14092449890389394,0.11338533115801606,0.19630381987696596,0.15770080435107395,0.16912173342969045,0.12297077939849581,0.12570138569116607}
    };

    // flow[i][j]表示从微云i到微云j的工作流大小
    // 且flow[i][j]=-flow[j][i]
    double[][] flow=new double[num+2][num+2];


    // 初始化flow
    void initFlow(){
        for(int i=0;i<num+2;i++){
            for(int j=0;j<num+2;j++){
                flow[i][j]=0;
            }
        }
    }


    // 初始化微云
    void initCloudlet(Cloudlet[] cloudlet){
        for(int i=0;i<num;i++){
            Cloudlet tmpCloudlet=new Cloudlet();
            tmpCloudlet.arrivalRate=lamda[i];
            tmpCloudlet.numServers=number[i];
            tmpCloudlet.serviceRate=mu[i];
            cloudlet[i]=tmpCloudlet;
            calCloudlet(cloudlet[i],i);
        }
    }

    void calCloudlet(Cloudlet cloudlet,int i){
        calFinalFlow(cloudlet,i);
        calTaskWaitTime(cloudlet,cloudlet.finalFlow);
        calSumNetDelay(cloudlet,i);
        calTaskResTime(cloudlet);
    }


    // 计算微云的任务平均等待时间
    void calTaskWaitTime(Cloudlet cloudlet,double arrivalRate){
        int numServers=cloudlet.numServers;
        double serviceRate=cloudlet.serviceRate;
        double tmp=formulaErlangC(numServers,arrivalRate/serviceRate);
        double result=tmp/(numServers*serviceRate-arrivalRate)+1/serviceRate;

        cloudlet.taskWaitTime= result;
    }

    // 计算微云i上的总网络延迟
    void calSumNetDelay(Cloudlet cloudlet,int i){
        double sum=0;
        for(int j=0;j<num;j++){
            sum+=Math.max(flow[j][i],0)*netDelay[j][i];
        }
        cloudlet.sumNetDelay=sum;
    }

    // 计算微云i上的平均响应时间
    void calTaskResTime(Cloudlet cloudlet){
        cloudlet.taskResTime= cloudlet.taskWaitTime+cloudlet.sumNetDelay;
    }

    // 计算微云i上最终剩余的工作流量
    void calFinalFlow(Cloudlet cloudlet,int i){
        double inFlow=0;
        for(int j=0;j<num;j++){
            inFlow+=flow[i][j];
        }
        cloudlet.finalFlow=cloudlet.arrivalRate-inFlow;
    }


    // Erlang C公式，用以计算平均等待时间
    double  formulaErlangC(int n,double p){
        double result=0;  // 函数的返回结果
        double numerator; // 公式的分子
        double denominator; // 公式的分母
        double sum=0; // 公式中分母的求和部分

        for(int k=0;k<n;k++){
            sum+=fastPow(n*p,k)/factorial(k);
        }

        numerator=fastPow(n*p,n)/(factorial(n)*(1-p));
        denominator=sum+numerator;
        result=numerator/denominator;

        return result;
    }


    // 快速幂计算a的b次方
    double fastPow(double a,int b){
        double base=a;
        int m=b;
        int res=1;
        while(m!=0){
            if((m&1)==1){
                res*=base;
            }
            base*=base;
            m = m >> 1;
        }
        return res;
    }


    // 计算n的阶乘
    double factorial(int n){
        int tmp=2;
        for(int i=3;i<=n;i++){
            tmp*=i;
        }
        return tmp;
    }
}
