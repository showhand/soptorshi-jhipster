/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { QuotationDetailsService } from 'app/entities/quotation-details/quotation-details.service';
import {
    IQuotationDetails,
    QuotationDetails,
    Currency,
    UnitOfMeasurements,
    PayType,
    VatStatus,
    AITStatus,
    WarrantyStatus
} from 'app/shared/model/quotation-details.model';

describe('Service Tests', () => {
    describe('QuotationDetails Service', () => {
        let injector: TestBed;
        let service: QuotationDetailsService;
        let httpMock: HttpTestingController;
        let elemDefault: IQuotationDetails;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(QuotationDetailsService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new QuotationDetails(
                0,
                Currency.TAKA,
                0,
                UnitOfMeasurements.PCS,
                0,
                PayType.CASH,
                0,
                VatStatus.EXCLUDED,
                0,
                AITStatus.EXCLUDED,
                0,
                currentDate,
                WarrantyStatus.WARRANTY,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                currentDate
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        estimatedDate: currentDate.format(DATE_FORMAT),
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

            it('should create a QuotationDetails', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        estimatedDate: currentDate.format(DATE_FORMAT),
                        modifiedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        estimatedDate: currentDate,
                        modifiedOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new QuotationDetails(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a QuotationDetails', async () => {
                const returnedFromService = Object.assign(
                    {
                        currency: 'BBBBBB',
                        rate: 1,
                        unitOfMeasurements: 'BBBBBB',
                        quantity: 1,
                        payType: 'BBBBBB',
                        creditLimit: 1,
                        vatStatus: 'BBBBBB',
                        vatPercentage: 1,
                        aitStatus: 'BBBBBB',
                        aitPercentage: 1,
                        estimatedDate: currentDate.format(DATE_FORMAT),
                        warrantyStatus: 'BBBBBB',
                        loadingPort: 'BBBBBB',
                        remarks: 'BBBBBB',
                        modifiedBy: 'BBBBBB',
                        modifiedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        estimatedDate: currentDate,
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

            it('should return a list of QuotationDetails', async () => {
                const returnedFromService = Object.assign(
                    {
                        currency: 'BBBBBB',
                        rate: 1,
                        unitOfMeasurements: 'BBBBBB',
                        quantity: 1,
                        payType: 'BBBBBB',
                        creditLimit: 1,
                        vatStatus: 'BBBBBB',
                        vatPercentage: 1,
                        aitStatus: 'BBBBBB',
                        aitPercentage: 1,
                        estimatedDate: currentDate.format(DATE_FORMAT),
                        warrantyStatus: 'BBBBBB',
                        loadingPort: 'BBBBBB',
                        remarks: 'BBBBBB',
                        modifiedBy: 'BBBBBB',
                        modifiedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        estimatedDate: currentDate,
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

            it('should delete a QuotationDetails', async () => {
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
