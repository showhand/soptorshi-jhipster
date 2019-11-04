/* tslint:disable max-line-length */
import { getTestBed, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { map, take } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CommercialPaymentInfoService } from 'app/entities/commercial-payment-info/commercial-payment-info.service';
import {
    CommercialCurrency,
    CommercialPaymentCategory,
    CommercialPaymentInfo,
    ICommercialPaymentInfo
} from 'app/shared/model/commercial-payment-info.model';

describe('Service Tests', () => {
    describe('CommercialPaymentInfo Service', () => {
        let injector: TestBed;
        let service: CommercialPaymentInfoService;
        let httpMock: HttpTestingController;
        let elemDefault: ICommercialPaymentInfo;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(CommercialPaymentInfoService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new CommercialPaymentInfo(
                0,
                CommercialPaymentCategory.UNDEFINED,
                0,
                CommercialCurrency.BDT,
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
                        createOn: currentDate.format(DATE_FORMAT),
                        updatedOn: currentDate.format(DATE_FORMAT)
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

            it('should create a CommercialPaymentInfo', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        createOn: currentDate.format(DATE_FORMAT),
                        updatedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createOn: currentDate,
                        updatedOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new CommercialPaymentInfo(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a CommercialPaymentInfo', async () => {
                const returnedFromService = Object.assign(
                    {
                        paymentCategory: 'BBBBBB',
                        totalAmount: 1,
                        currencyType: 'BBBBBB',
                        paymentTerms: 'BBBBBB',
                        createdBy: 'BBBBBB',
                        createOn: currentDate.format(DATE_FORMAT),
                        updatedBy: 'BBBBBB',
                        updatedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        createOn: currentDate,
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

            it('should return a list of CommercialPaymentInfo', async () => {
                const returnedFromService = Object.assign(
                    {
                        paymentCategory: 'BBBBBB',
                        totalAmount: 1,
                        currencyType: 'BBBBBB',
                        paymentTerms: 'BBBBBB',
                        createdBy: 'BBBBBB',
                        createOn: currentDate.format(DATE_FORMAT),
                        updatedBy: 'BBBBBB',
                        updatedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createOn: currentDate,
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

            it('should delete a CommercialPaymentInfo', async () => {
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
