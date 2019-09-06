/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { DebtorLedgerService } from 'app/entities/debtor-ledger/debtor-ledger.service';
import { IDebtorLedger, DebtorLedger, BillClosingFlag } from 'app/shared/model/debtor-ledger.model';

describe('Service Tests', () => {
    describe('DebtorLedger Service', () => {
        let injector: TestBed;
        let service: DebtorLedgerService;
        let httpMock: HttpTestingController;
        let elemDefault: IDebtorLedger;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(DebtorLedgerService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new DebtorLedger(
                0,
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                0,
                0,
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

            it('should create a DebtorLedger', async () => {
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
                    .create(new DebtorLedger(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a DebtorLedger', async () => {
                const returnedFromService = Object.assign(
                    {
                        serialNo: 'BBBBBB',
                        billNo: 'BBBBBB',
                        billDate: currentDate.format(DATE_FORMAT),
                        amount: 1,
                        paidAmount: 1,
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

            it('should return a list of DebtorLedger', async () => {
                const returnedFromService = Object.assign(
                    {
                        serialNo: 'BBBBBB',
                        billNo: 'BBBBBB',
                        billDate: currentDate.format(DATE_FORMAT),
                        amount: 1,
                        paidAmount: 1,
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

            it('should delete a DebtorLedger', async () => {
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
