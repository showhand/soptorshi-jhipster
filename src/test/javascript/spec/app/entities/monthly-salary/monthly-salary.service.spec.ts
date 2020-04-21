/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { MonthlySalaryService } from 'app/entities/monthly-salary/monthly-salary.service';
import { IMonthlySalary, MonthlySalary, MonthType, MonthlySalaryStatus } from 'app/shared/model/monthly-salary.model';

describe('Service Tests', () => {
    describe('MonthlySalary Service', () => {
        let injector: TestBed;
        let service: MonthlySalaryService;
        let httpMock: HttpTestingController;
        let elemDefault: IMonthlySalary;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(MonthlySalaryService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new MonthlySalary(
                0,
                0,
                MonthType.JANUARY,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                false,
                false,
                MonthlySalaryStatus.APPROVED_BY_MANAGER,
                'AAAAAAA',
                currentDate,
                false
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        modifiedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a MonthlySalary', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        modifiedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        modifiedOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new MonthlySalary(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a MonthlySalary', async () => {
                const returnedFromService = Object.assign(
                    {
                        year: 1,
                        month: 'BBBBBB',
                        basic: 1,
                        gross: 1,
                        houseRent: 1,
                        medicalAllowance: 1,
                        overTimeAllowance: 1,
                        foodAllowance: 1,
                        arrearAllowance: 1,
                        driverAllowance: 1,
                        fuelLubAllowance: 1,
                        mobileAllowance: 1,
                        travelAllowance: 1,
                        otherAllowance: 1,
                        festivalAllowance: 1,
                        absent: 1,
                        fine: 1,
                        advanceHO: 1,
                        advanceFactory: 1,
                        providentFund: 1,
                        tax: 1,
                        loanAmount: 1,
                        billPayable: 1,
                        billReceivable: 1,
                        payable: 1,
                        approved: true,
                        onHold: true,
                        status: 'BBBBBB',
                        modifiedBy: 'BBBBBB',
                        modifiedOn: currentDate.format(DATE_FORMAT),
                        voucherGenerated: true
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        modifiedOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of MonthlySalary', async () => {
                const returnedFromService = Object.assign(
                    {
                        year: 1,
                        month: 'BBBBBB',
                        basic: 1,
                        gross: 1,
                        houseRent: 1,
                        medicalAllowance: 1,
                        overTimeAllowance: 1,
                        foodAllowance: 1,
                        arrearAllowance: 1,
                        driverAllowance: 1,
                        fuelLubAllowance: 1,
                        mobileAllowance: 1,
                        travelAllowance: 1,
                        otherAllowance: 1,
                        festivalAllowance: 1,
                        absent: 1,
                        fine: 1,
                        advanceHO: 1,
                        advanceFactory: 1,
                        providentFund: 1,
                        tax: 1,
                        loanAmount: 1,
                        billPayable: 1,
                        billReceivable: 1,
                        payable: 1,
                        approved: true,
                        onHold: true,
                        status: 'BBBBBB',
                        modifiedBy: 'BBBBBB',
                        modifiedOn: currentDate.format(DATE_FORMAT),
                        voucherGenerated: true
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        modifiedOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a MonthlySalary', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
