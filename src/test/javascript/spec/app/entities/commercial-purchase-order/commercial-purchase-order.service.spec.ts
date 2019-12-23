/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CommercialPurchaseOrderService } from 'app/entities/commercial-purchase-order/commercial-purchase-order.service';
import { ICommercialPurchaseOrder, CommercialPurchaseOrder } from 'app/shared/model/commercial-purchase-order.model';

describe('Service Tests', () => {
    describe('CommercialPurchaseOrder Service', () => {
        let injector: TestBed;
        let service: CommercialPurchaseOrderService;
        let httpMock: HttpTestingController;
        let elemDefault: ICommercialPurchaseOrder;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(CommercialPurchaseOrderService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new CommercialPurchaseOrder(
                0,
                'AAAAAAA',
                currentDate,
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
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
                        purchaseOrderDate: currentDate.format(DATE_FORMAT),
                        shipmentDate: currentDate.format(DATE_FORMAT),
                        createdOn: currentDate.format(DATE_FORMAT),
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

            it('should create a CommercialPurchaseOrder', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        purchaseOrderDate: currentDate.format(DATE_FORMAT),
                        shipmentDate: currentDate.format(DATE_FORMAT),
                        createdOn: currentDate.format(DATE_FORMAT),
                        updatedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        purchaseOrderDate: currentDate,
                        shipmentDate: currentDate,
                        createdOn: currentDate,
                        updatedOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new CommercialPurchaseOrder(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a CommercialPurchaseOrder', async () => {
                const returnedFromService = Object.assign(
                    {
                        purchaseOrderNo: 'BBBBBB',
                        purchaseOrderDate: currentDate.format(DATE_FORMAT),
                        originOfGoods: 'BBBBBB',
                        finalDestination: 'BBBBBB',
                        shipmentDate: currentDate.format(DATE_FORMAT),
                        createdBy: 'BBBBBB',
                        createdOn: currentDate.format(DATE_FORMAT),
                        updatedBy: 'BBBBBB',
                        updatedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        purchaseOrderDate: currentDate,
                        shipmentDate: currentDate,
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

            it('should return a list of CommercialPurchaseOrder', async () => {
                const returnedFromService = Object.assign(
                    {
                        purchaseOrderNo: 'BBBBBB',
                        purchaseOrderDate: currentDate.format(DATE_FORMAT),
                        originOfGoods: 'BBBBBB',
                        finalDestination: 'BBBBBB',
                        shipmentDate: currentDate.format(DATE_FORMAT),
                        createdBy: 'BBBBBB',
                        createdOn: currentDate.format(DATE_FORMAT),
                        updatedBy: 'BBBBBB',
                        updatedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        purchaseOrderDate: currentDate,
                        shipmentDate: currentDate,
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

            it('should delete a CommercialPurchaseOrder', async () => {
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
