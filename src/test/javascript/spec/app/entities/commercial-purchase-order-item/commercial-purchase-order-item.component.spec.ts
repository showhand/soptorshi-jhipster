/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialPurchaseOrderItemComponent } from 'app/entities/commercial-purchase-order-item/commercial-purchase-order-item.component';
import { CommercialPurchaseOrderItemService } from 'app/entities/commercial-purchase-order-item/commercial-purchase-order-item.service';
import { CommercialPurchaseOrderItem } from 'app/shared/model/commercial-purchase-order-item.model';

describe('Component Tests', () => {
    describe('CommercialPurchaseOrderItem Management Component', () => {
        let comp: CommercialPurchaseOrderItemComponent;
        let fixture: ComponentFixture<CommercialPurchaseOrderItemComponent>;
        let service: CommercialPurchaseOrderItemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialPurchaseOrderItemComponent],
                providers: [
                    {
                        provide: ActivatedRoute,
                        useValue: {
                            data: {
                                subscribe: (fn: (value: Data) => void) =>
                                    fn({
                                        pagingParams: {
                                            predicate: 'id',
                                            reverse: false,
                                            page: 0
                                        }
                                    })
                            }
                        }
                    }
                ]
            })
                .overrideTemplate(CommercialPurchaseOrderItemComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CommercialPurchaseOrderItemComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommercialPurchaseOrderItemService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new CommercialPurchaseOrderItem(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.commercialPurchaseOrderItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });

        it('should load a page', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new CommercialPurchaseOrderItem(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.loadPage(1);

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.commercialPurchaseOrderItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });

        it('should re-initialize the page', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new CommercialPurchaseOrderItem(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.loadPage(1);
            comp.reset();

            // THEN
            expect(comp.page).toEqual(0);
            expect(service.query).toHaveBeenCalledTimes(2);
            expect(comp.commercialPurchaseOrderItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
        it('should calculate the sort attribute for an id', () => {
            // WHEN
            const result = comp.sort();

            // THEN
            expect(result).toEqual(['id,asc']);
        });

        it('should calculate the sort attribute for a non-id attribute', () => {
            // GIVEN
            comp.predicate = 'name';

            // WHEN
            const result = comp.sort();

            // THEN
            expect(result).toEqual(['name,asc', 'id']);
        });
    });
});
