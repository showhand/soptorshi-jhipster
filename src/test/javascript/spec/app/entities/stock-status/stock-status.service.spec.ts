/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { StockStatusService } from 'app/entities/stock-status/stock-status.service';
import { IStockStatus, StockStatus, UnitOfMeasurements } from 'app/shared/model/stock-status.model';

describe('Service Tests', () => {
    describe('StockStatus Service', () => {
        let injector: TestBed;
        let service: StockStatusService;
        let httpMock: HttpTestingController;
        let elemDefault: IStockStatus;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(StockStatusService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new StockStatus(0, 'AAAAAAA', 0, UnitOfMeasurements.PCS, 0, 0, 0, 'AAAAAAA', currentDate);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        stockInDate: currentDate.format(DATE_TIME_FORMAT)
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

            it('should create a StockStatus', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        stockInDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        stockInDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new StockStatus(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a StockStatus', async () => {
                const returnedFromService = Object.assign(
                    {
                        containerTrackingId: 'BBBBBB',
                        totalQuantity: 1,
                        unit: 'BBBBBB',
                        availableQuantity: 1,
                        totalPrice: 1,
                        availablePrice: 1,
                        stockInBy: 'BBBBBB',
                        stockInDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        stockInDate: currentDate
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

            it('should return a list of StockStatus', async () => {
                const returnedFromService = Object.assign(
                    {
                        containerTrackingId: 'BBBBBB',
                        totalQuantity: 1,
                        unit: 'BBBBBB',
                        availableQuantity: 1,
                        totalPrice: 1,
                        availablePrice: 1,
                        stockInBy: 'BBBBBB',
                        stockInDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        stockInDate: currentDate
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

            it('should delete a StockStatus', async () => {
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
