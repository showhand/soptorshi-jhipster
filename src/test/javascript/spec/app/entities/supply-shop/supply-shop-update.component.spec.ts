/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplyShopUpdateComponent } from 'app/entities/supply-shop/supply-shop-update.component';
import { SupplyShopService } from 'app/entities/supply-shop/supply-shop.service';
import { SupplyShop } from 'app/shared/model/supply-shop.model';

describe('Component Tests', () => {
    describe('SupplyShop Management Update Component', () => {
        let comp: SupplyShopUpdateComponent;
        let fixture: ComponentFixture<SupplyShopUpdateComponent>;
        let service: SupplyShopService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplyShopUpdateComponent]
            })
                .overrideTemplate(SupplyShopUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SupplyShopUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SupplyShopService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new SupplyShop(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.supplyShop = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new SupplyShop();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.supplyShop = entity;
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
