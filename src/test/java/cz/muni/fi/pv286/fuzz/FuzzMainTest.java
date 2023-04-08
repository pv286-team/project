package cz.muni.fi.pv286.fuzz;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;
import cz.muni.fi.pv286.arguments.InvalidArgumentsException;
import cz.muni.fi.pv286.arguments.ProgramArguments;

import java.util.ArrayList;
import java.util.List;

class FuzzMainTest {
    @FuzzTest
    void argumentFuzzTest(FuzzedDataProvider data) {
        List<String> argumentList = new ArrayList<>();
        int numberOfArguments = data.consumeInt(1, 20);
        for (int i = 0; i < numberOfArguments; i++) {
            String arg = data.consumeString(10);
            argumentList.add(arg);
        }
        try {
            String[] args = argumentList.toArray(new String[0]);
            new ProgramArguments(args);
        } catch (InvalidArgumentsException ignored) {
        } catch (Exception e) {
            assert(false);
        }
        assert(true);
    }
}
