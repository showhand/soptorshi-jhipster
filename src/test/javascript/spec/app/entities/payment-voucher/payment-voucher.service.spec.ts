/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PaymentVoucherService } from 'app/entities/payment-voucher/payment-voucher.service';
import { IPaymentVoucher, PaymentVoucher, ApplicationType } from 'app/shared/model/payment-voucher.model';

describe('Service Tests', () => {
    describe('PaymentVoucher Service', () => {
        let injector: TestBed;
        let service: PaymentVoucherService;
        let httpMock: HttpTestingController;
        let elemDefault: IPaymentVoucher;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(PaymentVoucherService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new PaymentVoucher(
                0,
                'AAAAAAA',
                currentDate,
                currentDate,
                ApplicationType.REQUISITION,
                0,
                'AAAAAAA',
                currentDate
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        voucherDate: currentDate.format(DATE_FORMAT),
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

            it('should create a PaymentVoucher', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        voucherDate: currentDate.format(DATE_FORMAT),
                        postDate: currentDate.format(DATE_FORMAT),
                        modifiedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        voucherDate: currentDate,
                        postDate: currentDate,
                        modifiedOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new PaymentVoucher(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a PaymentVoucher', async () => {
                const returnedFromService = Object.assign(
                    {
                        voucherNo: 'BBBBBB',
                        voucherDate: currentDate.format(DATE_FORMAT),
                        postDate: currentDate.format(DATE_FORMAT),
                        applicationType: 'BBBBBB',
                        applicationId: 1,
                        modifiedBy: 'BBBBBB',
                        modifiedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        voucherDate: currentDate,
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

            it('should return a list of PaymentVoucher', async () => {
                const returnedFromService = Object.assign(
                    {
                        voucherNo: 'BBBBBB',
                        voucherDate: currentDate.format(DATE_FORMAT),
                        postDate: currentDate.format(DATE_FORMAT),
                        applicationType: 'BBBBBB',
                        applicationId: 1,
                        modifiedBy: 'BBBBBB',
                        modifiedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        voucherDate: currentDate,
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

            it('should delete a PaymentVoucher', async () => {
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
