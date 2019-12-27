/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialProductInfoUpdateComponent } from 'app/entities/commercial-product-info/commercial-product-info-update.component';
import { CommercialProductInfoService } from 'app/entities/commercial-product-info/commercial-product-info.service';
import { CommercialProductInfo } from 'app/shared/model/commercial-product-info.model';

describe('Component Tests', () => {
    describe('CommercialProductInfo Management Update Component', () => {
        let comp: CommercialProductInfoUpdateComponent;
        let fixture: ComponentFixture<CommercialProductInfoUpdateComponent>;
        let service: CommercialProductInfoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialProductInfoUpdateComponent]
            })
                .overrideTemplate(CommercialProductInfoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CommercialProductInfoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommercialProductInfoService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CommercialProductInfo(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.commercialProductInfo = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CommercialProductInfo();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.commercialProductInfo = entity;
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
