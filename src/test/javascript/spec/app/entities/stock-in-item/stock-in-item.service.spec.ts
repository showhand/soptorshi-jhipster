/* tslint:disable max-line-length */
import { getTestBed, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { map, take } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { StockInItemService } from 'app/entities/stock-in-item/stock-in-item.service';
import { ContainerCategory, IStockInItem, ProductType, StockInItem, UnitOfMeasurements } from 'app/shared/model/stock-in-item.model';

describe('Service Tests', () => {
    describe('StockInItem Service', () => {
        let injector: TestBed;
        let service: StockInItemService;
        let httpMock: HttpTestingController;
        let elemDefault: IStockInItem;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(StockInItemService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new StockInItem(
                0,
                0,
                UnitOfMeasurements.PCS,
                0,
                ContainerCategory.BOTTLE,
                'AAAAAAA',
                currentDate,
                currentDate,
                ProductType.FINISHED_PRODUCT,
                'AAAAAAA',
                currentDate,
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        mfgDate: currentDate.format(DATE_FORMAT),
                        expiryDate: currentDate.format(DATE_FORMAT),
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

            it('should create a StockInItem', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        mfgDate: currentDate.format(DATE_FORMAT),
                        expiryDate: currentDate.format(DATE_FORMAT),
                        stockInDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        mfgDate: currentDate,
                        expiryDate: currentDate,
                        stockInDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new StockInItem(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a StockInItem', async () => {
                const returnedFromService = Object.assign(
                    {
                        quantity: 1,
                        unit: 'BBBBBB',
                        price: 1,
                        containerCategory: 'BBBBBB',
                        containerTrackingId: 'BBBBBB',
                        mfgDate: currentDate.format(DATE_FORMAT),
                        expiryDate: currentDate.format(DATE_FORMAT),
                        typeOfProduct: 'BBBBBB',
                        stockInBy: 'BBBBBB',
                        stockInDate: currentDate.format(DATE_TIME_FORMAT),
                        remarks: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        mfgDate: currentDate,
                        expiryDate: currentDate,
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

            it('should return a list of StockInItem', async () => {
                const returnedFromService = Object.assign(
                    {
                        quantity: 1,
                        unit: 'BBBBBB',
                        price: 1,
                        containerCategory: 'BBBBBB',
                        containerTrackingId: 'BBBBBB',
                        mfgDate: currentDate.format(DATE_FORMAT),
                        expiryDate: currentDate.format(DATE_FORMAT),
                        typeOfProduct: 'BBBBBB',
                        stockInBy: 'BBBBBB',
                        stockInDate: currentDate.format(DATE_TIME_FORMAT),
                        remarks: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        mfgDate: currentDate,
                        expiryDate: currentDate,
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

            it('should delete a StockInItem', async () => {
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
