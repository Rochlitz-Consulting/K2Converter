package org.rochlitz.K2Converter.type.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rochlitz.K2Converter.type.record.GenericRecord;
import org.rochlitz.K2Converter.type.record.types.InsertRecord;

public class InsertConverterProcessorTest {

    private InsertConverterProcessor processor;
    private Exchange exchange;

    @BeforeEach
    public void setUp() {
        processor = new InsertConverterProcessor();
        exchange = new DefaultExchange(new DefaultCamelContext());
    }

    @Test
    public void process_shouldConvertGenericRecordToInsertRecord() throws ClassNotFoundException {
        GenericRecord record = new GenericRecord("K", Arrays.asList(
        "3000341011"
            ,"Die Gesamtdosis sollte nicht ohne R\\u25cksprache mit einem Arzt oder Apotheker \\u25berschritten werden.~n~n~kArt der Anwendung?~k~nNehmen Sie das Arzneimittel unverd\\u25nnt ein. Sie k\\o25nnen das Arzneimittel aber auch mit Wasser oder Tee verd\\u25nnen.~n~n~kDauer der Anwendung?~k~nDie Anwendungsdauer ist nicht begrenzt. Das Arzneimittel ist f\\u25r eine Behandlung \\u25ber einen l\\a25ngeren Zeitraum geeignet und sollte mindestens \\u25ber 3 Monatszyklen erfolgen.~n~n~k\\U25berdosierung?~k~nEs sind keine \\U25berdosierungserscheinungen bekannt. Im Zweifelsfall wenden Sie sich an Ihren Arzt.~n~n~kAnwendung vergessen?~k~nSetzen Sie die Anwendung zum n\\a25chsten vorgeschriebenen Zeitpunkt ganz normal (also nicht mit der doppelten Menge) fort.~n~nGenerell gilt: Achten Sie vor allem bei S\\a25uglingen, Kleinkindern und \\a25lteren Menschen auf eine gewissenhafte Dosierung. Im Zweifelsfalle fragen Sie Ihren Arzt oder Apotheker nach etwaigen Auswirkungen oder Vorsichtsma\\s39nahmen.~n~nEine vom Arzt v"
            ,"~kAufbewahrung~k~n~nDas Arzneimittel darf nach Anbruch/Zubereitung h\\o25chstens 2 Monate verwendet werden\\324~nDas Arzneimittel muss nach Anbruch/Zubereitung bei Raumtemperatur aufbewahrt werden\\324"
            ,"20120920"
            ,"Tropfen"
            ,"~kWas spricht gegen eine Anwendung?~k~n~n- \\U25berempfindlichkeit gegen die Inhaltsstoffe~n- Brusttumore~n- Tumore der Hirnanhangsdr\\u25se (Hypophysentumore)~n~n~kWelche Altersgruppe ist zu beachten?~k~n- Kinder unter 12 Jahren: Das Arzneimittel sollte in dieser Altersgruppe in der Regel nicht angewendet werden.~n~n~kWas ist mit Schwangerschaft und Stillzeit?~k~n- Schwangerschaft: Das Arzneimittel sollte nach derzeitigen Erkenntnissen nicht angewendet werden.~n- Stillzeit: Von einer Anwendung wird nach derzeitigen Erkenntnissen abgeraten. Eventuell ist ein Abstillen in Erw\\a25gung zu ziehen.~n~nIst Ihnen das Arzneimittel trotz einer Gegenanzeige verordnet worden, sprechen Sie mit Ihrem Arzt oder Apotheker. Der therapeutische Nutzen kann h\\o25her sein, als das Risiko, das die Anwendung bei einer Gegenanzeige in sich birgt."
            , "1"
            ,"~kWelche unerw\\u25nschten Wirkungen k\\o25nnen auftreten?~k~n~n- Hautausschlag~n- Juckreiz~n- Nesselausschlag~n~nBemerken Sie eine Befindlichkeitsst\\o25rung oder Ver\\a25nderung w\\a25hrend der Behandlung, wenden Sie sich an Ihren Arzt oder Apotheker.~n~nF\\u25r die Information an dieser Stelle werden vor allem Nebenwirkungen ber\\u25cksichtigt, die bei mindestens einem von 1.000 behandelten Patienten auftreten."
            ,"1"
            ,"Agnolyt MADAUS"
            ,"5253"
            ,"AGNOLYTMADAUS            00002"
            ,"AGNOLYTMADAUS"
            ,"0"
            ,"0"
            , "~kWas sollten Sie beachten?~k~n- Vorsicht bei Allergie gegen Gew\\u25rze, \\a25therische \\O25le und Terpentin\\o25l\\324~n- Das Arzneimittel enth\\a25lt Alkohol und stellt somit ein Risiko f\\u25r Leberkranke, Alkoholiker, Epileptiker, Hirngesch\\a25digte, Schwangere, Stillende und Kinder dar.~n"
        ,"~kWie wirkt der Inhaltsstoff des Arzneimittels?~k~n~nDie Inhaltsstoffe entstammen der Pflanze M\\o25nchspfeffer und wirken als nat\\u25rliches Gemisch. Zu der Pflanze selbst:~n~n~i- Aussehen: bis zu 6~gm hoher Baum oder Strauch, dessen Fiederbl\\a25tter auf der Unterseite behaart sind, die kleinen duftenden violetten Bl\\u25ten bilden \\a25hrenartige Bl\\u25tenst\\a25nde, die Fr\\u25chte sind klein, r\\o25tlich-schwarz~n~i- Vorkommen: S\\u25deuropa, Asien~n~i- Haupts\\a25chliche Inhaltsstoffe: \\a25therisches \\O25l (Limonen, Pinen), Iridoidglykoside (Aucubin, Agnosid), Flavonoide~n~i- Verwendete Pflanzenteile und Zubereitungen: haupts\\a25chlich Extrakte der Frucht~nExtrakte von M\\o25nchspfeffer unterdr\\u25cken die Freisetzung von Prolaktin. Die Konzentration dieser Substanz ist vor der Menstruation h\\a25ufig erh\\o25ht und ist eine der Ursachen f\\u25r die pr\\a25menstruellen Beschwerden. Zus\\a25tzlich scheint M\\o25nchspfeffer das Gleichgewicht zwischen \\O25strogen und Progesteron wiederherzustellen."
        ));

        exchange.getIn().setBody(record);

        processor.process(exchange);

        InsertRecord insertRecord = exchange.getIn().getBody(InsertRecord.class);
        assertNotNull(insertRecord);
        assertEquals(insertRecord.getValues().get(0), "3000341011");//TODO test all fields
        assertEquals(insertRecord.getValues().get(1), "Die Gesamtdosis sollte nicht ohne R\\u25cksprache mit einem Arzt oder Apotheker \\u25berschritten werden.~n~n~kArt der Anwendung?~k~nNehmen Sie das Arzneimittel unverd\\u25nnt ein. Sie k\\o25nnen das Arzneimittel aber auch mit Wasser oder Tee verd\\u25nnen.~n~n~kDauer der Anwendung?~k~nDie Anwendungsdauer ist nicht begrenzt. Das Arzneimittel ist f\\u25r eine Behandlung \\u25ber einen l\\a25ngeren Zeitraum geeignet und sollte mindestens \\u25ber 3 Monatszyklen erfolgen.~n~n~k\\U25berdosierung?~k~nEs sind keine \\U25berdosierungserscheinungen bekannt. Im Zweifelsfall wenden Sie sich an Ihren Arzt.~n~n~kAnwendung vergessen?~k~nSetzen Sie die Anwendung zum n\\a25chsten vorgeschriebenen Zeitpunkt ganz normal (also nicht mit der doppelten Menge) fort.~n~nGenerell gilt: Achten Sie vor allem bei S\\a25uglingen, Kleinkindern und \\a25lteren Menschen auf eine gewissenhafte Dosierung. Im Zweifelsfalle fragen Sie Ihren Arzt oder Apotheker nach etwaigen Auswirkungen oder Vorsichtsma\\s39nahmen.~n~nEine vom Arzt v");
        assertEquals(insertRecord.getValues().get(16), "~kWie wirkt der Inhaltsstoff des Arzneimittels?~k~n~nDie Inhaltsstoffe entstammen der Pflanze M\\o25nchspfeffer und wirken als nat\\u25rliches Gemisch. Zu der Pflanze selbst:~n~n~i- Aussehen: bis zu 6~gm hoher Baum oder Strauch, dessen Fiederbl\\a25tter auf der Unterseite behaart sind, die kleinen duftenden violetten Bl\\u25ten bilden \\a25hrenartige Bl\\u25tenst\\a25nde, die Fr\\u25chte sind klein, r\\o25tlich-schwarz~n~i- Vorkommen: S\\u25deuropa, Asien~n~i- Haupts\\a25chliche Inhaltsstoffe: \\a25therisches \\O25l (Limonen, Pinen), Iridoidglykoside (Aucubin, Agnosid), Flavonoide~n~i- Verwendete Pflanzenteile und Zubereitungen: haupts\\a25chlich Extrakte der Frucht~nExtrakte von M\\o25nchspfeffer unterdr\\u25cken die Freisetzung von Prolaktin. Die Konzentration dieser Substanz ist vor der Menstruation h\\a25ufig erh\\o25ht und ist eine der Ursachen f\\u25r die pr\\a25menstruellen Beschwerden. Zus\\a25tzlich scheint M\\o25nchspfeffer das Gleichgewicht zwischen \\O25strogen und Progesteron wiederherzustellen.");
    }

}