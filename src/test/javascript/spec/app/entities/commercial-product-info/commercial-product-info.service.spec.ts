/* tslint:disable max-line-length */
import { getTestBed, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { map, take } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CommercialProductInfoService } from 'app/entities/commercial-product-info/commercial-product-info.service';
import {
    CommercialProductInfo,
    ICommercialProductInfo,
    PackColor,
    ProductSpecification,
    SurfaceType,
    UnitOfMeasurements
} from 'app/shared/model/commercial-product-info.model';

describe('Service Tests', () => {
    describe('CommercialProductInfo Service', () => {
        let injector: TestBed;
        let service: CommercialProductInfoService;
        let httpMock: HttpTestingController;
        let elemDefault: ICommercialProductInfo;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(CommercialProductInfoService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new CommercialProductInfo(
                0,
                0,
                ProductSpecification.FILLET,
                'AAAAAAA',
                0,
                UnitOfMeasurements.PCS,
                0,
                0,
                0,
                SurfaceType.TRIMMED,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                0,
                0,
                PackColor.PLAIN,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                0,
                0,
                PackColor.PLAIN,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                0,
                'AAAAAAA',
                'AAAAAAA',
                0,
                0,
                0,
                UnitOfMeasurements.PCS,
                0,
                0,
                0,
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
                        createdOn: currentDate.format(DATE_TIME_FORMAT),
                        updatedOn: currentDate.format(DATE_TIME_FORMAT)
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

            it('should create a CommercialProductInfo', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        createdOn: currentDate.format(DATE_TIME_FORMAT),
                        updatedOn: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createdOn: currentDate,
                        updatedOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new CommercialProductInfo(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a CommercialProductInfo', async () => {
                const returnedFromService = Object.assign(
                    {
                        taskNo: 1,
                        productSpecification: 'BBBBBB',
                        spSize: 'BBBBBB',
                        offeredQuantity: 1,
                        offeredUnit: 'BBBBBB',
                        offeredUnitPrice: 1,
                        offeredTotalPrice: 1,
                        spGlazing: 1,
                        spSurfaceType: 'BBBBBB',
                        spOthersDescription: 'BBBBBB',
                        spSticker: 'BBBBBB',
                        spLabel: 'BBBBBB',
                        spQtyInPack: 1,
                        spQtyInMc: 1,
                        ipColor: 'BBBBBB',
                        ipSize: 'BBBBBB',
                        ipSticker: 'BBBBBB',
                        ipLabel: 'BBBBBB',
                        ipQtyInMc: 1,
                        ipCost: 1,
                        mcColor: 'BBBBBB',
                        mcPly: 'BBBBBB',
                        mcSize: 'BBBBBB',
                        mcSticker: 'BBBBBB',
                        mcLabel: 'BBBBBB',
                        mcCost: 1,
                        cylColor: 'BBBBBB',
                        cylSize: 'BBBBBB',
                        cylQty: 1,
                        cylCost: 1,
                        buyingQuantity: 1,
                        buyingUnit: 'BBBBBB',
                        buyingUnitPrice: 1,
                        buyingPrice: 1,
                        buyingTotalPrice: 1,
                        createdBy: 'BBBBBB',
                        createdOn: currentDate.format(DATE_TIME_FORMAT),
                        updatedBy: 'BBBBBB',
                        updatedOn: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
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

            it('should return a list of CommercialProductInfo', async () => {
                const returnedFromService = Object.assign(
                    {
                        taskNo: 1,
                        productSpecification: 'BBBBBB',
                        spSize: 'BBBBBB',
                        offeredQuantity: 1,
                        offeredUnit: 'BBBBBB',
                        offeredUnitPrice: 1,
                        offeredTotalPrice: 1,
                        spGlazing: 1,
                        spSurfaceType: 'BBBBBB',
                        spOthersDescription: 'BBBBBB',
                        spSticker: 'BBBBBB',
                        spLabel: 'BBBBBB',
                        spQtyInPack: 1,
                        spQtyInMc: 1,
                        ipColor: 'BBBBBB',
                        ipSize: 'BBBBBB',
                        ipSticker: 'BBBBBB',
                        ipLabel: 'BBBBBB',
                        ipQtyInMc: 1,
                        ipCost: 1,
                        mcColor: 'BBBBBB',
                        mcPly: 'BBBBBB',
                        mcSize: 'BBBBBB',
                        mcSticker: 'BBBBBB',
                        mcLabel: 'BBBBBB',
                        mcCost: 1,
                        cylColor: 'BBBBBB',
                        cylSize: 'BBBBBB',
                        cylQty: 1,
                        cylCost: 1,
                        buyingQuantity: 1,
                        buyingUnit: 'BBBBBB',
                        buyingUnitPrice: 1,
                        buyingPrice: 1,
                        buyingTotalPrice: 1,
                        createdBy: 'BBBBBB',
                        createdOn: currentDate.format(DATE_TIME_FORMAT),
                        updatedBy: 'BBBBBB',
                        updatedOn: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
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

            it('should delete a CommercialProductInfo', async () => {
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
