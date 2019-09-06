/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CreditorLedgerService } from 'app/entities/creditor-ledger/creditor-ledger.service';
import { ICreditorLedger, CreditorLedger, BalanceType, BillClosingFlag } from 'app/shared/model/creditor-ledger.model';

describe('Service Tests', () => {
    describe('CreditorLedger Service', () => {
        let injector: TestBed;
        let service: CreditorLedgerService;
        let httpMock: HttpTestingController;
        let elemDefault: ICreditorLedger;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(CreditorLedgerService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new CreditorLedger(
                0,
                0,
                'AAAAAAA',
                currentDate,
                0,
                0,
                BalanceType.DEBIT,
                BillClosingFlag.OPEN,
                currentDate,
                'AAAAAAA',
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
                        billDate: currentDate.format(DATE_FORMAT),
                        dueDate: currentDate.format(DATE_FORMAT),
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

            it('should create a CreditorLedger', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        billDate: currentDate.format(DATE_FORMAT),
                        dueDate: currentDate.format(DATE_FORMAT),
                        modifiedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        billDate: currentDate,
                        dueDate: currentDate,
                        modifiedOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new CreditorLedger(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a CreditorLedger', async () => {
                const returnedFromService = Object.assign(
                    {
                        serialNo: 1,
                        billNo: 'BBBBBB',
                        billDate: currentDate.format(DATE_FORMAT),
                        amount: 1,
                        paidAmount: 1,
                        balanceType: 'BBBBBB',
                        billClosingFlag: 'BBBBBB',
                        dueDate: currentDate.format(DATE_FORMAT),
                        vatNo: 'BBBBBB',
                        contCode: 'BBBBBB',
                        orderNo: 'BBBBBB',
                        modifiedBy: 'BBBBBB',
                        modifiedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        billDate: currentDate,
                        dueDate: currentDate,
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

            it('should return a list of CreditorLedger', async () => {
                const returnedFromService = Object.assign(
                    {
                        serialNo: 1,
                        billNo: 'BBBBBB',
                        billDate: currentDate.format(DATE_FORMAT),
                        amount: 1,
                        paidAmount: 1,
                        balanceType: 'BBBBBB',
                        billClosingFlag: 'BBBBBB',
                        dueDate: currentDate.format(DATE_FORMAT),
                        vatNo: 'BBBBBB',
                        contCode: 'BBBBBB',
                        orderNo: 'BBBBBB',
                        modifiedBy: 'BBBBBB',
                        modifiedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        billDate: currentDate,
                        dueDate: currentDate,
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

            it('should delete a CreditorLedger', async () => {
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
