OKYS (Öğrenci Kayıt ve Yönetim Sistemi)
Projenin Tanımı ve Amacı
OKYS, üniversite ortamında öğrenci, akademisyen, ders, kayıt ve not bilgilerinin yönetimini kolaylaştırmak için geliştirilmiş Java tabanlı bir web uygulamasıdır. Bu sistem sayesinde öğrenci kayıt işlemleri, ders atamaları ve not girişleri gibi işlemler tek bir merkezi platform üzerinden verimli bir şekilde gerçekleştirilebilir. Uygulama, RESTful API mimarisi üzerine kuruludur ve istemci taleplerini HTTP üzerinden alıp JSON formatında yanıtlar üretir. Veriler PostgreSQL ilişkisel veritabanında tutulur ve uygulama, bulut üzerinde Railway platformu kullanılarak dağıtılmıştır. Bu sayede hem geliştirme hem de dağıtım aşamalarında esneklik ve ölçeklenebilirlik sağlanmıştır.

Kullanılan Teknolojiler
Java (JDK 17+): Proje, en az Java 17 sürümü ile çalışacak şekilde geliştirilmiştir. Modern Java sürümleri, dilin sunduğu yeni özellikler ve performans iyileştirmeleriyle birlikte uygulamanın güvenli ve verimli olmasını sağlar. JDK 17, LTS (Long Term Support) sürümü olduğu için kararlılık ve uzun vadeli destek avantajı da sunar.

Spring Boot: Spring Framework’ün üzerinde yükselen, bağımsız ve üretime hazır uygulamalar oluşturmayı kolaylaştıran bir Java çatısıdır. Spring Boot, kapsamlı otomatik yapılandırma özellikleriyle projeyi hızlı şekilde ayağa kaldırmamıza imkan tanır. Geliştiricilerin karmaşık XML yapılandırmalarıyla uğraşmasını engelleyip “konfigürasyon yerine varsayılanlar” yaklaşımı sunar. Böylece temel amaç olan uygulama geliştirme sürecini hızlandırma ve sadeleştirme hedefine ulaşılır.

Spring Data JPA: Spring ekosisteminin bir parçası olan bu modül, JPA (Java Persistence API) kullanarak veritabanı işlemlerini kolaylaştırır. Uygulamada, veritabanı ile etkileşim Hibernate ORM kütüphanesi üzerinden gerçekleştirilmekte ve Spring Data JPA sayesinde CRUD işlemleri için gereken altyapı büyük ölçüde otomatik sağlanmaktadır. Kısaca, JPA standardını uygulayan (Hibernate, EclipseLink gibi) sağlayıcılar aracılığıyla ilişkisel veritabanları üzerinde işlem yapmayı basitleştiren bir Spring projesidir.

PostgreSQL: Uygulamanın kalıcı verileri için PostgreSQL kullanılmıştır. PostgreSQL, güçlü ve açık kaynaklı bir ilişkisel veritabanı yönetim sistemidir. Özellikle tutarlılık, güvenilirlik ve ölçeklenebilirlik gerektiren kurumsal uygulamalar için uygundur. OKYS, öğrenci ve ders bilgilerinden not kayıtlarına kadar tüm verileri PostgreSQL üzerinde saklayarak ACID uyumlu işlemler ve ilişkisel sorgulama imkanı sunar.

JSON: JavaScript Object Notation – uygulamanın istemci ile iletişimi için kullanılan veri formatıdır. Hem istek hem de cevaplar JSON biçiminde olduğundan, sistem teknoloji bağımsız bir şekilde farklı platformlarla entegre olabilir. JSON, insan tarafından okunabilir olması ve yaygın biçimde desteklenmesi sayesinde API’nin kullanımını basitleştirir.

REST API: OKYS, REST (Representational State Transfer) mimari prensiplerine uygun olarak geliştirilmiştir. Bu sayede sistemdeki her kaynak (öğrenci, akademisyen, ders vb.), benzersiz URI uç noktalarıyla temsil edilir ve bu kaynaklar üzerinde gerçekleştirilen işlemler HTTP metodları (GET, POST, PUT, DELETE) ile tanımlanır. REST API yaklaşımı, istemci-sunucu ayrımını netleştirir, durumsuz (stateless) iletişimi ve standartlaştırılmış bir yapı sunar. Sonuç olarak, farklı istemci uygulamaları (web, mobil vb.) aynı servisleri kullanarak OKYS ile kolayca etkileşim kurabilir.

Railway: Uygulamanın bulut ortamında dağıtımı için Railway platformu tercih edilmiştir. Railway, tıpkı Heroku benzeri bir yaklaşımla, depo bağlantısı kurarak uygulamayı otomatik olarak derleyip sunabilen bir PaaS ortamıdır. Bu platform üzerinde PostgreSQL veritabanı servisi oluşturulmuş ve uygulamanın çevresel değişkenleri tanımlanarak konfigürasyon yapılmıştır. Railway, git deposuna entegre bir şekilde sürekli dağıtım (CI/CD) olanağı sağladığı gibi ölçeklendirme ve izleme gibi konularda da yardımcı olur.

Proje Yapısı

Bu proje, geleneksel katmanlı mimari kullanılarak yapılandırılmıştır. Kod, sorumluluklara göre ayrılmış dört ana katmana bölünmüştür:
Entity (Varlık) Katmanı: Uygulamanın temel veri modellerini barındırır. Her entity sınıfı, veritabanındaki bir tabloya karşılık gelir ve o tabloyla ilgili alanları içerir. Örneğin, sistemde öğrencileri temsil eden OgrKayit sınıfı, akademik personeli temsil eden Akademisyen sınıfı ve dersleri temsil eden Ders sınıfı bu katmanda yer alır. Bu varlıklar arasındaki ilişkiler (bir öğrencinin birden fazla derse kayıt olması gibi) yine bu katmanda JPA anotasyonları ile tanımlanır.

Repository (Depo) Katmanı: Veritabanı erişim katmanıdır. Spring Data JPA kullanılarak her entity için bir repository arayüzü oluşturulmuştur (örneğin, OgrKayitRepository, AkademisyenRepository, DersRepository). Bu arayüzler sayesinde temel CRUD (Create, Read, Update, Delete) operasyonları ve gerektiğinde özel sorgular kolaylıkla gerçekleştirilir. Repository katmanı, veriye erişim detaylarını üstlenir ve üst katmanlara sade bir API sunar.

Service (İş Servisi) Katmanı: Uygulamanın iş mantığı bu katmanda yer alır. Service sınıfları (örneğin OgrKayitServisi, AkademisyenServisi) bir veya birden çok repository ile etkileşime girerek gereken işlemleri gerçekleştirir. Bu katman, controller ile repository arasında bir köprü görevi görür; verilerin doğrulanması, işlem sıralaması, birden fazla kaynağın birlikte kullanımı gibi operasyonel mantık burada kurgulanır. İş mantığının servislerde toplanması, kodun tekrar kullanılabilirliğini artırır ve controller katmanını sade tutar.

Controller (Denetleyici) Katmanı: REST API uç noktalarının tanımlandığı katmandır. HTTP isteklerini karşılar ve uygun servisi çağırarak gelen talepleri işler. Örneğin, OgrKayitController sınıfı gelen öğrenci ile ilgili istekleri (listeleme, ekleme, güncelleme, silme) alır ve ilgili servis metodlarını çağırır. Controller katmanı, isteklere uygun HTTP yanıtlarını üretir; bunlar genellikle JSON formatında veri ve uygun HTTP durum kodlarıdır. Bu katman, kullanıcı arayüzü ile servis katmanı arasında bir kontrol noktası işlevi görür ve gelen isteklerin doğruluğunu (path ve parametreler) kontrol eder.
Bu katmanlı yapı sayesinde, her bir bileşen kendi sorumluluk alanına odaklanır. Örneğin, veritabanı değişikliği gerektiğinde sadece repository veya entity katmanı etkilenirken, iş kurallarındaki bir değişiklik yalnızca servis katmanında yapılır. Böylece bakım kolaylığı ve sürdürülebilirlik elde edilir.

Kurulum ve Çalıştırma

Aşağıdaki adımları izleyerek OKYS uygulamasını yerel ortamınızda çalıştırabilirsiniz:
Gereksinimler: Öncelikle sisteminizde Java JDK 17 (veya üzeri) kurulu olmalıdır. Ayrıca projenin derlenip çalıştırılması için Apache Maven aracına ihtiyaç vardır. Maven, bağımlılıkların yönetimi ve proje derleme sürecini yönetecektir. Son olarak, yerel geliştirme için PostgreSQL veritabanının kurulu ve çalışır durumda olduğundan emin olun.
Kaynak Kodun Edinilmesi: Proje kaynak kodunu GitHub deposundan klonlayın veya bir zip dosyası olarak indirin. Örneğin, git kullanarak komut satırında aşağıdaki komutla depoyu klonlayabilirsiniz (URL ve projenin gerçek adına göre düzenleyin):git clone https://github.com/kullaniciadi/okys-proje.git
Ardından proje dizinine geçin: cd okys-proje.

Veritabanı Kurulumu: PostgreSQL üzerinde uygulama için bir veritabanı ve kullanıcı oluşturun. Örneğin, universite_db adında bir veritabanı ve bu veritabanına erişecek universite_user adında bir kullanıcı tanımlayalım. Bu kullanıcıya basit bir şifre (örn. sifre123) verebilir ve gerekli yetkileri atayabiliriz. PostgreSQL komut satırında veya pgAdmin gibi bir araçta aşağıdaki SQL komutlarını çalıştırabilirsiniz:
CREATE DATABASE universite_db;
CREATE USER universite_user WITH PASSWORD 'sifre123';
GRANT ALL PRIVILEGES ON DATABASE universite_db TO universite_user;
Not: İstediğiniz farklı bir veritabanı adı, kullanıcı adı ve parola belirleyebilirsiniz. Yukarıdaki değerler sadece örnek olarak verilmiştir. Ayrıca, PostgreSQL sunucunuzun localhost:5432 portunda çalıştığı varsayılmıştır; farklı bir port ya da uzak sunucu kullanıyorsanız bağlantı ayarlarını uygun şekilde düzenlemelisiniz.
Yapılandırma (application.properties): Proje dizinindeki src/main/resources/application.properties dosyasını açarak veritabanı bağlantı ayarlarını güncelleyin. Bu dosyada, Spring Boot uygulamasının PostgreSQL veritabanına bağlanması için gerekli bilgileri tanımlıyoruz:
spring.datasource.url=jdbc:postgresql://localhost:5432/universite_db
spring.datasource.username=universite_user
spring.datasource.password=sifre123
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
Yukarıdaki ayarlar, uygulamanın universite_db veritabanına belirtilen kullanıcı ile bağlanmasını sağlar. ddl-auto=update ayarı, JPA’nın varlık sınıflarına göre tabloları otomatik oluşturup güncellemesine olanak tanır (geliştirme aşamasında kullanışlıdır). Gereksinimlerinize göre bunu validate, create, none gibi başka bir değere çevirebilirsiniz. Önemli: Gerçek bir üretim ortamında hassas veritabanı bilgilerini kodda tutmak yerine çevre değişkenleri ile beslemek daha güvenlidir (aşağıda Railway kısmına bakınız).
Uygulamayı Çalıştırma: Tüm ayarlamalar yapıldıktan sonra projeyi çalıştırabilirsiniz. Maven kullanarak uygulamayı başlatmak için aşağıdaki komutu terminalde proje dizininde çalıştırın:
mvn spring-boot:run
Bu komut, gerekli tüm bağımlılıkları indirip projeyi derleyecek ve yerleşik Tomcat sunucusu üzerinde uygulamayı http://localhost:8080 adresinde başlatacaktır. Alternatif olarak, Maven ile bir .jar dosyası oluşturup çalıştırabilirsiniz:
mvn clean package
java -jar target/okys-1.0.0.jar
(Gerçek jar dosyasının adı ve versiyonu pom.xml içerisindeki ayarlara göre değişebilir.) Uygulama başladıktan sonra, loglarda "Started OkysApplication" benzeri bir ifade görüyorsanız başarılı bir şekilde çalışıyor demektir.
Test Etme: Uygulamanın çalıştığını doğrulamak için herhangi bir REST istemci aracı kullanabilirsiniz. Örneğin, tarayıcınızda http://localhost:8080/api/ogrkayitlar adresine bir GET isteği yapmak, sistemde kayıtlı tüm öğrencileri JSON formatında döndürecektir (ilk başta veri yoksa boş bir liste dönecektir). Benzer şekilde Postman gibi bir araçla yeni bir öğrenci eklemek için POST http://localhost:8080/api/ogrkayitlar isteği gönderip gövdeye uygun JSON verisini ekleyerek API’yi deneyebilirsiniz.
API Dökümantasyonu
OKYS uygulaması, öğrenci, akademisyen ve ders yönetimi için RESTful API uç noktaları sunmaktadır. Aşağıda her bir kaynak türü için mevcut HTTP istekleri ve örnek kullanımlar listelenmiştir.
Öğrenci (OgrKayit) Uç Noktaları
GET /api/ogrkayitlar – Tüm öğrenci kayıtlarını listeler. Sunucudan başarıyla yanıt alındığında, dönen veri örnek olarak şöyle görünebilir:
[
  {
    "id": 1,
    "adi": "Ahmet",
    "soyadi": "Yılmaz",
    "ogrenciNo": "2021001",
    "bolum": "Bilgisayar Mühendisliği"
  },
  {
    "id": 2,
    "adi": "Ayşe",
    "soyadi": "Öztürk",
    "ogrenciNo": "2021002",
    "bolum": "Elektrik-Elektronik Mühendisliği"
  }
]
Bu uç nokta, sistemdeki tüm öğrencileri bir dizi (array) içerisinde JSON olarak döndürür.
GET /api/ogrkayitlar/{id} – Belirli bir öğrenci kaydını id değerine göre getirir. Örneğin /api/ogrkayitlar/1 çağrıldığında id’si 1 olan öğrencinin detayları JSON olarak dönecektir. Eğer belirtilen id’ye ait bir kayıt yoksa, sunucu 404 Not Found hatası döndürecektir.
POST /api/ogrkayitlar – Yeni bir öğrenci kaydı oluşturur. İstek gövdesinde oluşturulacak öğrenciye ait bilgiler JSON formatında gönderilmelidir. Örneğin, yeni bir öğrenci eklemek için gönderilen JSON şöyle olabilir:
{
  "adi": "Mehmet",
  "soyadi": "Kaya",
  "ogrenciNo": "2023003",
  "bolum": "Makine Mühendisliği"
}
Başarılı olursa sunucu, oluşturulan kaynağın JSON temsilini ve 201 Created durum kodunu döndürür. Oluşturulan kaydın kalıcı kimliği (id) bu yanıt içerisinde görülebilir.
PUT /api/ogrkayitlar/{id} – Mevcut bir öğrenci kaydını günceller. Belirtilen {id} değerine sahip öğrenciye ait alanları, istek gövdesinde verilen JSON verisiyle değiştirir. Gönderilen JSON, POST örneğindekiyle benzer bir yapıda olmalıdır (örneğin adı veya diğer alanları güncellenmiş şekilde). Eğer {id} değerine sahip bir öğrenci yoksa 404 Not Found, başarılı ise güncellenmiş nesnenin JSON’ı ve 200 OK döner.
DELETE /api/ogrkayitlar/{id} – Belirli bir öğrenci kaydını sistemden siler. Bu çağrı başarılı olduğunda sunucu genellikle içeriksiz bir 204 No Content yanıtı döndürür. Silinmek istenen id bulunamazsa 404 Not Found dönecektir.
Akademisyen Uç Noktaları
GET /api/akademisyenler – Tüm akademisyen (öğretim görevlisi) kayıtlarını listeler ve JSON dizisi olarak döndürür.
GET /api/akademisyenler/{id} – Tek bir akademisyenin detaylarını döndürür.
POST /api/akademisyenler – Yeni bir akademisyen eklemek için kullanılır. İstek gövdesine eklenecek akademisyenin bilgileri JSON olarak verilmelidir. Örnek:
{
  "adi": "Deniz",
  "soyadi": "Şahin",
  "unvan": "Doç. Dr.",
  "bolum": "Matematik"
}
Bu isteğe karşılık başarılı ekleme durumunda, eklenen akademisyenin bilgileri (id değeriyle birlikte) JSON formatında döner.
PUT /api/akademisyenler/{id} – Mevcut bir akademisyen bilgisini günceller. Gövdeye, güncellenecek alanları içeren JSON verilmelidir. Örneğin, bir akademisyenin unvanını veya bölümünü güncellemek için bu uç nokta kullanılabilir.
DELETE /api/akademisyenler/{id} – İlgili {id} değerine sahip akademisyen kaydını siler. Başarılı ise 204 No Content yanıtı döner.
Ders Uç Noktaları
GET /api/dersler – Sistemde tanımlı tüm dersleri listeler. Yanıt, her bir ders için temel bilgileri (ders adı, kodu, kredi gibi) içeren JSON dizisi olacaktır.
GET /api/dersler/{id} – Belirli bir dersi id numarasına göre getirir.
POST /api/dersler – Yeni bir ders oluşturmak için kullanılır. Gönderilen JSON örneği aşağıdaki gibi olabilir:
{
  "dersAdi": "Veri Yapıları",
  "dersKodu": "CS201",
  "kredi": 4,
  "akademisyenId": 5
}
Bu örnekte yeni dersin adı, kodu ve kredisi belirtilmiştir. İsteğe opsiyonel olarak akademisyenId alanı eklenerek dersi veren akademisyenin, sistemde kayıtlı ilgili ID’si ile ilişkilendirilmesi sağlanır. Başarılı ekleme sonrasında oluşturulan dersin verileri JSON olarak döner.
PUT /api/dersler/{id} – Mevcut bir dersin bilgilerini günceller. Örneğin dersin adını veya kredisini değiştirmek için gerekli alanları JSON formatında göndererek bu uç noktayı çağırabilirsiniz.
DELETE /api/dersler/{id} – Belirtilen id numarasına sahip dersi sistemden siler. İlgili ders ve o derse ait kayıtlar silindikten sonra başarı durumunda 204 No Content yanıtı dönecektir.
Not: Tüm POST ve PUT isteklerinde, içerik tipi (Content-Type) olarak application/json kullanılması gerekmektedir. Ayrıca, API istekleri sırasında hatalı veya eksik veri gönderimi durumunda sunucu, 400 Bad Request gibi uygun hata kodları ve açıklayıcı mesajlar döndürebilir. Güvenlik ve yetkilendirme ile ilgili konular bu temel sürümde ele alınmamıştır; ancak Spring Boot’un sağladığı Spring Security gibi ek kütüphanelerle API’ye kimlik doğrulama kolaylıkla eklenebilir.
Railway Deployment (Dağıtım)
OKYS uygulaması, geliştirme ortamında başarılı bir şekilde çalıştıktan sonra, bulut üzerinde Railway platformu kullanılarak yayınlanmıştır. Railway üzerinde dağıtım, aşağıdaki temel adımlarla gerçekleştirilmiştir (bilgi amaçlı özetlenmiştir):
Railway Projesi Oluşturma: Öncelikle Railway hesabında yeni bir proje oluşturuldu. Ardından, GitHub depomuz Railway’e entegre edilerek Continuous Deployment ayarlandı. Bu sayede depoya yaptığımız her push sonrasında Railway uygulamayı otomatik olarak yeniden inşa edip yayına almaktadır.
Veritabanı Servisi Ekleme: Railway, proje paneli üzerinden bir PostgreSQL veritabanı eklememize olanak tanır. OKYS için Railway üzerinden bir PostgreSQL servisi oluşturuldu. Bu işlem sonucunda platform, veritabanı bağlantısı için gerekli ortam değişkenlerini otomatik olarak tanımladı (örneğin PGHOST, PGUSER, PGPASSWORD, PGDATABASE gibi)
docs.railway.com
. Bu değişkenler, veritabanının host adresini, portunu, kullanıcı adını, şifresini ve veritabanı ismini içermektedir.
Ortam Değişkenlerinin Yapılandırılması: Spring Boot uygulamasının, Railway tarafından sağlanan bu ortam değişkenlerini kullanarak veritabanına bağlanabilmesi için uygulama yapılandırması güncellendi. Örneğin, application.properties içinde sabit bağlantı URL’si yerine aşağıdaki gibi bir tanımlama yapılmıştır:
spring.datasource.url=jdbc:postgresql://${PGHOST}:${PGPORT}/${PGDATABASE}
spring.datasource.username=${PGUSER}
spring.datasource.password=${PGPASSWORD}
Benzer şekilde, SPRING_DATASOURCE_URL gibi değişkenler de doğrudan tanımlanabilir. Spring Boot, ortam değişkenlerini otomatik olarak okuyup ilgili değerlere atama yapabildiğinden (özellikle değişken isimleri SPRING_ ile başladığında), kaynak kodda hassas bilgi tutulmasına gerek kalmamıştır. Railway üzerinde, proje ayarları altından gerekli değişkenler doğrulanmış ve gerekirse manuel olarak düzenlenmiştir. Sonuç olarak uygulama, bulut ortamında çalışırken veritabanı bağlantı bilgilerini Railway’in sağladığı gizli değişkenlerden alacak şekilde güvenli hale getirilmiştir.
Dağıtım ve Erişim: Yapılandırma tamamlandıktan sonra Railway, uygulamayı konteyner içinde çalıştırarak internet üzerinden erişilebilir hale getirdi. Uygulamaya ait bir alt alan adı (domain) otomatik olarak oluşturulur (genellikle <proje-adı>.railway.app). Bu sayede, API uç noktalarına internet üzerinden istek yapılabilir. Örneğin, OKYS servisinin dağıtımı sonrası, https://<proje-adı>.railway.app/api/ogrkayitlar URL’ine bir GET isteği göndererek buluttaki uygulamadan öğrenci kayıtları çekilebilir.
Loglar ve İzleme: Railway arayüzü üzerinden uygulamanın logları gerçek zamanlı olarak izlenebilir. Dağıtım sırasında veya sonrasında oluşabilecek hatalar bu loglar yardımıyla tespit edilip giderilmiştir. Ayrıca, Railway projesi panelinde uygulamanın çalıştığı konteynerin durumu, CPU/RAM kullanımı gibi metrikler de gözlemlenebilir.
Railway üzerinde bu şekilde yapılandırılan OKYS, herhangi bir ekstra sunucu kurulum işiyle uğraşmadan bulutta çalıştırılmıştır. Sonuç olarak, geliştirme aşamasında yerelde çalışan uygulama, minimal değişiklikle bulut ortamında da çalışacak hale getirilmiştir. Bu yaklaşım, uygulamanın gerçek dünyada kullanılabilmesine bir adım daha yaklaştırır ve gerektiğinde ölçeklendirilebilir bir altyapı sunar.
