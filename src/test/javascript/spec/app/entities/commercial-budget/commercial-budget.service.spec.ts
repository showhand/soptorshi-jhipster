/* tslint:disable max-line-length */
import { getTestBed, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { map, take } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CommercialBudgetService } from 'app/entities/commercial-budget/commercial-budget.service';
import {
    CommercialBudget,
    CommercialBudgetStatus,
    CommercialCustomerCategory,
    CommercialOrderCategory,
    ICommercialBudget,
    PaymentType,
    TransportType
} from 'app/shared/model/commercial-budget.model';

describe('Service Tests', () => {
    describe('CommercialBudget Service', () => {
        let injector: TestBed;
        let service: CommercialBudgetService;
        let httpMock: HttpTestingController;
        let elemDefault: ICommercialBudget;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(CommercialBudgetService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new CommercialBudget(
                0,
                'AAAAAAA',
                CommercialOrderCategory.LOCAL,
                CommercialCustomerCategory.ZONE,
                currentDate,
                'AAAAAAA',
                PaymentType.LC,
                TransportType.CFR,
                'AAAAAAA',
                0,
                'AAAAAAA',
                0,
                'AAAAAAA',
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                CommercialBudgetStatus.SAVE_AS_DRAFT,
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                'AAAAAAA',
                currentDate
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        budgetDate: currentDate.format(DATE_FORMAT),
                        createdOn: currentDate.format(DATE_TIME_FORMAT),
                        updatedOn: currentDate.format(DATE_TIME_FORMAT)
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

            it('should create a CommercialBudget', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        budgetDate: currentDate.format(DATE_FORMAT),
                        createdOn: currentDate.format(DATE_TIME_FORMAT),
                        updatedOn: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        budgetDate: currentDate,
                        createdOn: currentDate,
                        updatedOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new CommercialBudget(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a CommercialBudget', async () => {
                const returnedFromService = Object.assign(
                    {
                        budgetNo: 'BBBBBB',
                        type: 'BBBBBB',
                        customer: 'BBBBBB',
                        budgetDate: currentDate.format(DATE_FORMAT),
                        companyName: 'BBBBBB',
                        paymentType: 'BBBBBB',
                        transportationType: 'BBBBBB',
                        seaPortName: 'BBBBBB',
                        seaPortCost: 1,
                        airPortName: 'BBBBBB',
                        airPortCost: 1,
                        landPortName: 'BBBBBB',
                        landPortCost: 1,
                        insurancePrice: 1,
                        totalTransportationCost: 1,
                        totalQuantity: 1,
                        totalOfferedPrice: 1,
                        totalBuyingPrice: 1,
                        profitAmount: 1,
                        profitPercentage: 1,
                        budgetStatus: 'BBBBBB',
                        proformaNo: 'BBBBBB',
                        createdBy: 'BBBBBB',
                        createdOn: currentDate.format(DATE_TIME_FORMAT),
                        updatedBy: 'BBBBBB',
                        updatedOn: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        budgetDate: currentDate,
                        createdOn: currentDate,
                        updatedOn: currentDate
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

            it('should return a list of CommercialBudget', async () => {
                const returnedFromService = Object.assign(
                    {
                        budgetNo: 'BBBBBB',
                        type: 'BBBBBB',
                        customer: 'BBBBBB',
                        budgetDate: currentDate.format(DATE_FORMAT),
                        companyName: 'BBBBBB',
                        paymentType: 'BBBBBB',
                        transportationType: 'BBBBBB',
                        seaPortName: 'BBBBBB',
                        seaPortCost: 1,
                        airPortName: 'BBBBBB',
                        airPortCost: 1,
                        landPortName: 'BBBBBB',
                        landPortCost: 1,
                        insurancePrice: 1,
                        totalTransportationCost: 1,
                        totalQuantity: 1,
                        totalOfferedPrice: 1,
                        totalBuyingPrice: 1,
                        profitAmount: 1,
                        profitPercentage: 1,
                        budgetStatus: 'BBBBBB',
                        proformaNo: 'BBBBBB',
                        createdBy: 'BBBBBB',
                        createdOn: currentDate.format(DATE_TIME_FORMAT),
                        updatedBy: 'BBBBBB',
                        updatedOn: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        budgetDate: currentDate,
                        createdOn: currentDate,
                        updatedOn: currentDate
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

            it('should delete a CommercialBudget', async () => {
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
