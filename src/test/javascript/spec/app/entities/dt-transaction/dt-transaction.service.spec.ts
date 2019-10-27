/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { DtTransactionService } from 'app/entities/dt-transaction/dt-transaction.service';
import { IDtTransaction, DtTransaction, BalanceType, VoucherType, InstrumentType } from 'app/shared/model/dt-transaction.model';

describe('Service Tests', () => {
    describe('DtTransaction Service', () => {
        let injector: TestBed;
        let service: DtTransactionService;
        let httpMock: HttpTestingController;
        let elemDefault: IDtTransaction;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(DtTransactionService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new DtTransaction(
                0,
                'AAAAAAA',
                currentDate,
                0,
                0,
                BalanceType.DEBIT,
                VoucherType.SELLING,
                'AAAAAAA',
                currentDate,
                InstrumentType.CHEQUE,
                'AAAAAAA',
                currentDate,
                0,
                0,
                currentDate,
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        voucherDate: currentDate.format(DATE_FORMAT),
                        invoiceDate: currentDate.format(DATE_FORMAT),
                        instrumentDate: currentDate.format(DATE_FORMAT),
                        postDate: currentDate.format(DATE_FORMAT),
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

            it('should create a DtTransaction', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        voucherDate: currentDate.format(DATE_FORMAT),
                        invoiceDate: currentDate.format(DATE_FORMAT),
                        instrumentDate: currentDate.format(DATE_FORMAT),
                        postDate: currentDate.format(DATE_FORMAT),
                        modifiedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        voucherDate: currentDate,
                        invoiceDate: currentDate,
                        instrumentDate: currentDate,
                        postDate: currentDate,
                        modifiedOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new DtTransaction(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a DtTransaction', async () => {
                const returnedFromService = Object.assign(
                    {
                        voucherNo: 'BBBBBB',
                        voucherDate: currentDate.format(DATE_FORMAT),
                        serialNo: 1,
                        amount: 1,
                        balanceType: 'BBBBBB',
                        type: 'BBBBBB',
                        invoiceNo: 'BBBBBB',
                        invoiceDate: currentDate.format(DATE_FORMAT),
                        instrumentType: 'BBBBBB',
                        instrumentNo: 'BBBBBB',
                        instrumentDate: currentDate.format(DATE_FORMAT),
                        fCurrency: 1,
                        convFactor: 1,
                        postDate: currentDate.format(DATE_FORMAT),
                        narration: 'BBBBBB',
                        modifiedBy: 'BBBBBB',
                        modifiedOn: currentDate.format(DATE_FORMAT),
                        reference: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        voucherDate: currentDate,
                        invoiceDate: currentDate,
                        instrumentDate: currentDate,
                        postDate: currentDate,
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

            it('should return a list of DtTransaction', async () => {
                const returnedFromService = Object.assign(
                    {
                        voucherNo: 'BBBBBB',
                        voucherDate: currentDate.format(DATE_FORMAT),
                        serialNo: 1,
                        amount: 1,
                        balanceType: 'BBBBBB',
                        type: 'BBBBBB',
                        invoiceNo: 'BBBBBB',
                        invoiceDate: currentDate.format(DATE_FORMAT),
                        instrumentType: 'BBBBBB',
                        instrumentNo: 'BBBBBB',
                        instrumentDate: currentDate.format(DATE_FORMAT),
                        fCurrency: 1,
                        convFactor: 1,
                        postDate: currentDate.format(DATE_FORMAT),
                        narration: 'BBBBBB',
                        modifiedBy: 'BBBBBB',
                        modifiedOn: currentDate.format(DATE_FORMAT),
                        reference: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        voucherDate: currentDate,
                        invoiceDate: currentDate,
                        instrumentDate: currentDate,
                        postDate: currentDate,
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

            it('should delete a DtTransaction', async () => {
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
