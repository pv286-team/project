package cz.muni.fi.pv286.parser;

import cz.muni.fi.pv286.arguments.values.Option;
import cz.muni.fi.pv286.parser.input.PanbyteInput;
import cz.muni.fi.pv286.parser.input.PanbyteHexInput;
import cz.muni.fi.pv286.parser.input.PanbyteRawInput;
import cz.muni.fi.pv286.parser.output.PanbyteIntOutput;
import cz.muni.fi.pv286.parser.output.PanbyteOutput;
import cz.muni.fi.pv286.parser.output.PanbyteHexOutput;
import cz.muni.fi.pv286.parser.output.PanbyteRawOutput;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static cz.muni.fi.pv286.Main.processIO;


class PanbyteHexInputOutputTest {
    @Test
    void INPUT_Single_full() {
        String inputString = "68656C6C6F";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteHexInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("hello"));
    }

    @Test
    void INPUT_Single_full_high() {
        String inputString = "499602d2";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteHexOutput(stdoutWriter, true);
        final PanbyteInput input = new PanbyteHexInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("499602d2"));
    }

    @Test
    void INPUT_Single_full_high_noZeroPadding() {
        String inputString = "499602d2";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteHexOutput(stdoutWriter, false);
        final PanbyteInput input = new PanbyteHexInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("49962d2"));
    }


    @Test
    void INPUT_SingleWhiteSpaces_full() {
        String inputString = "68 65 6C 6C 6F";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteHexInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("hello"));
    }

    @Test
    void INPUT_Two_full() {
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();

        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteHexInput(output);

        try {
            input.parse(Util.byteList("68 65 6C"));
            input.parse(Util.byteList("6C 6F"));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("hello"));
    }

    @Test
    void INPUT_Two_half() {
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();

        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteHexInput(output);

        try {
            input.parse(Util.byteList("68 65 6"));
            input.parse(Util.byteList(" C6C 6F"));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("hello"));
    }

    @Test
    void OUTPUT_Two_full() {
        final String inputString = "hello";

        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();

        final PanbyteOutput output = new PanbyteHexOutput(stdoutWriter, true);
        final PanbyteInput input = new PanbyteRawInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("68656c6c6f"));
    }

    @Test
    void INPUTtoInt_4100int_big() {
        final String inputString = "01aa29eadc9f4f9e5621498e1c7913000db34be69032743aaa4a2aa465ae03c1058e17f2036b2e77" +
                "268b676b81d565fb68b24429f8551ead386fe933e179681093c3d3392bd9aa45ae4c4d9ea2ccb88a95d697897a7f5" +
                "8134e5a88a95fba94b76c2122bd8a2f19c2278f62b64fcbd8076b2b51c1c264110daf61bcdb97355a4aa18c1748fa" +
                "af07e9b52cd1e70a65937bd9f4c243cbafe6d7360c408c8d40c35b79cae0965a0cf7fb313a09edde675e4fa59b525" +
                "daf46b2bccc9029064c9587e65acd089cdff604baec16b5f0270e23da47173779fe4e0005e9a410db1a571947e654" +
                "bfee028e6a99e891b8ab316d7cd0865b5508f1d067af8bd9b6df1950908f3e4a09e594119b9c2211e6c13b4cabf37" +
                "3bbf488863d7fa21b4201b3928156043038eafa1b3e2d709ea853462e55b33792ea54222d2cc7c2582c44d711c15e" +
                "a2d99494b61c9e041771b976aa73d059ab7a8977941b994eebdb23cefb513ba6c603dde33884e9d070aa40dfdeb1d" +
                "17dc24429ecddf0330dea4998bc545a5f14471ddc743be9da9d7f4c003e5d46609e3ec7ddab48459ce7c172d0bd1e" +
                "3195c4b3fb6c92dc5103620d498ce6204fe988a323b0d0b5182d709723f761b64ea79dca45e2406264a545dd64002" +
                "074f11c46c8313af0df1fad33f616dd6c637d4e347615b0a70b2f324f0bbdfe9393b9d806740228fc59d31b0b6805" +
                "1fc8a14370cf302029b4696e84a4546f6b980179b0f54f4f3587e1ebafd57360b4cc852faeae9f872367b5a423179" +
                "72306ee44156c2364cfaddf17d7249c5c1c53a1bc2056f6cff26c94b0c7e8ba860268aa436f5cf24af48e171cb9f5" +
                "edfd6a15b4d7fcdadb63b5543260484a151447fbd07eb5567ec8615d50c634eeb10250ddee390d3224d6560befbbc" +
                "5e938d71613bdd6441d6ad57748e29f225acfe8e02a42422d4f95d14251ee6e872799a24bcfc2803af0878d46a4b1" +
                "ff092d902b3a4678cc86a9c956f5ee33c7293ec3ec6345a6a3a49b9ddd3246484c14a9adeb9ca9637d0d7509d499d" +
                "c2ced528362c2ec3e5b4a4b94d13e4d144aeab9a2d05095388a8b675d6db1471205ca5b551b2fb5bcd969e81e0e93" +
                "329003566259284e7fccd6dac857747e3fbbf2d3f1400a9d2da4edf41ea9a2e910d3680252de45af03798067d63b1" +
                "bef34bc38c1fce88cc796d5ada089f0bf925fb03ed447569fa443aba7f1b4d95ffa3ce88a13afb31678066074cc1b" +
                "36bb6b8490285742ad006a2b4cf995a4f9c595259c3b979fb87f1c3402ed5d3256032e6d273a70cc7c9d53e2e6864" +
                "114a1456f85b4bc56a79d5590dbff594203bdbf2bd57b400e74de6baee3c1b712f0fbe21ea1a1ee903f8caddedb80" +
                "e27168c60c4c48977af823ffbd4707442a2e136531d378fc8e31110f1ec84b0ec9d4dfc67ad4728c54cca1ad1b218" +
                "ac13e0fd6b8040fa0f6f4625e0484e17538196026f6930c646caa11ac7e392f4e5de55f8f33557a10292da9403940" +
                "96320da2d61d9fc15921aefea762728079ad2b6fc944965b20428fa152ad383c5f65e0df0cd5a28a402c5e76cd572" +
                "915d9aba756aa7cf13749a0eadef91b9d352b6cddd82d706084baf8b67b17136029d5005b700929c3b64d192a5b01" +
                "678027b7e6c081ec4b3bab0d99e431059a864372411a891411e38a13add2eb9b3159ac71c71c71c71c71c71c71c71" +
                "c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71" +
                "c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71" +
                "c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71" +
                "c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71" +
                "c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71" +
                "c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71" +
                "c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71" +
                "c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71" +
                "c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71" +
                "c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71" +
                "c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c71c7";

        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();

        final PanbyteOutput output = new PanbyteIntOutput(stdoutWriter, Option.BIG_ENDIAN);
        final PanbyteInput input = new PanbyteHexInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("11111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111"));
    }
}
