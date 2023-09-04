import com.taxi.dao.interfaces.CarDao
import com.taxi.exception.DataProcessingException
import com.taxi.model.Manufacturer
import com.taxi.services.CarServiceImpl
import com.taxi.services.DriverServiceImpl
import com.taxi.services.ManufacturerServiceImpl
import spock.lang.Specification

/**
 * TEST SERVICES ONLY !!1
 */
class FirstSpecification extends Specification{
    def "Fields"() {
        def manufacturerServiceImpl = new ManufacturerServiceImpl()
        def driverServiceImpl = new DriverServiceImpl()
        def carServiceImpl = new CarServiceImpl()
    }

    def "one plus one should equal two"() {
        expect:
        1 + 1 == 2
    }

    def "test manufacturer create one"() {
        given:
        def test1 = new Manufacturer(1, "test1Manufacturer", "Ukraine", false)

        when:
        String s = test1.name
        def id = 1

        then:
        s == "test1Manufacturer"
        id == 1
    }

    def "Errors block"() {
        when:
        new Manufacturer(null, null, null, true)

        then:
        thrown(DataProcessingException)
    }
}
