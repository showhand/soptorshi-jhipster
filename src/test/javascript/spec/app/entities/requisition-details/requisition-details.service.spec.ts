/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { RequisitionDetailsService } from 'app/entities/requisition-details/requisition-details.service';
import { IRequisitionDetails, RequisitionDetails, UnitOfMeasurements } from 'app/shared/model/requisition-details.model';

describe('Service Tests', () => {
    describe('RequisitionDetails Service', () => {
        let injector: TestBed;
        let service: RequisitionDetailsService;
        let httpMock: HttpTestingController;
        let elemDefault: IRequisitionDetails;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(RequisitionDetailsService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new RequisitionDetails(0, currentDate, currentDate, UnitOfMeasurements.PCS, 0, 0, 0, 'AAAAAAA', currentDate);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        requiredOn: currentDate.format(DATE_FORMAT),
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

            it('should create a RequisitionDetails', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        requiredOn: currentDate.format(DATE_FORMAT),
                        estimatedDate: currentDate.format(DATE_FORMAT),
                        modifiedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        requiredOn: currentDate,
                        estimatedDate: currentDate,
                        modifiedOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new RequisitionDetails(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a RequisitionDetails', async () => {
                const returnedFromService = Object.assign(
                    {
                        requiredOn: currentDate.format(DATE_FORMAT),
                        estimatedDate: currentDate.format(DATE_FORMAT),
                        uom: 'BBBBBB',
                        unit: 1,
                        unitPrice: 1,
                        quantity: 1,
                        modifiedBy: 'BBBBBB',
                        modifiedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        requiredOn: currentDate,
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

            it('should return a list of RequisitionDetails', async () => {
                const returnedFromService = Object.assign(
                    {
                        requiredOn: currentDate.format(DATE_FORMAT),
                        estimatedDate: currentDate.format(DATE_FORMAT),
                        uom: 'BBBBBB',
                        unit: 1,
                        unitPrice: 1,
                        quantity: 1,
                        modifiedBy: 'BBBBBB',
                        modifiedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        requiredOn: currentDate,
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

            it('should delete a RequisitionDetails', async () => {
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
