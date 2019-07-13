/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { ProductPriceUpdateComponent } from 'app/entities/product-price/product-price-update.component';
import { ProductPriceService } from 'app/entities/product-price/product-price.service';
import { ProductPrice } from 'app/shared/model/product-price.model';

describe('Component Tests', () => {
    describe('ProductPrice Management Update Component', () => {
        let comp: ProductPriceUpdateComponent;
        let fixture: ComponentFixture<ProductPriceUpdateComponent>;
        let service: ProductPriceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [ProductPriceUpdateComponent]
            })
                .overrideTemplate(ProductPriceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProductPriceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductPriceService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ProductPrice(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.productPrice = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ProductPrice();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.productPrice = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
