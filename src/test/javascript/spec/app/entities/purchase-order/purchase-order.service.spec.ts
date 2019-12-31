/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PurchaseOrderService } from 'app/entities/purchase-order/purchase-order.service';
import { IPurchaseOrder, PurchaseOrder, PurchaseOrderStatus } from 'app/shared/model/purchase-order.model';

describe('Service Tests', () => {
    describe('PurchaseOrder Service', () => {
        let injector: TestBed;
        let service: PurchaseOrderService;
        let httpMock: HttpTestingController;
        let elemDefault: IPurchaseOrder;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(PurchaseOrderService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new PurchaseOrder(
                0,
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                0,
                0,
                PurchaseOrderStatus.WAITING_FOR_ACCOUNTS_APPROVAL,
                'AAAAAAA',
                currentDate
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        issueDate: currentDate.format(DATE_FORMAT),
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

            it('should create a PurchaseOrder', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        issueDate: currentDate.format(DATE_FORMAT),
                        modifiedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        issueDate: currentDate,
                        modifiedOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new PurchaseOrder(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a PurchaseOrder', async () => {
                const returnedFromService = Object.assign(
                    {
                        purchaseOrderNo: 'BBBBBB',
                        workOrderNo: 'BBBBBB',
                        issueDate: currentDate.format(DATE_FORMAT),
                        referredTo: 'BBBBBB',
                        subject: 'BBBBBB',
                        note: 'BBBBBB',
                        laborOrOtherAmount: 1,
                        discount: 1,
                        status: 'BBBBBB',
                        modifiedBy: 'BBBBBB',
                        modifiedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        issueDate: currentDate,
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

            it('should return a list of PurchaseOrder', async () => {
                const returnedFromService = Object.assign(
                    {
                        purchaseOrderNo: 'BBBBBB',
                        workOrderNo: 'BBBBBB',
                        issueDate: currentDate.format(DATE_FORMAT),
                        referredTo: 'BBBBBB',
                        subject: 'BBBBBB',
                        note: 'BBBBBB',
                        laborOrOtherAmount: 1,
                        discount: 1,
                        status: 'BBBBBB',
                        modifiedBy: 'BBBBBB',
                        modifiedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        issueDate: currentDate,
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

            it('should delete a PurchaseOrder', async () => {
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
