/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplyChallanUpdateComponent } from 'app/entities/supply-challan/supply-challan-update.component';
import { SupplyChallanService } from 'app/entities/supply-challan/supply-challan.service';
import { SupplyChallan } from 'app/shared/model/supply-challan.model';

describe('Component Tests', () => {
    describe('SupplyChallan Management Update Component', () => {
        let comp: SupplyChallanUpdateComponent;
        let fixture: ComponentFixture<SupplyChallanUpdateComponent>;
        let service: SupplyChallanService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplyChallanUpdateComponent]
            })
                .overrideTemplate(SupplyChallanUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SupplyChallanUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SupplyChallanService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new SupplyChallan(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.supplyChallan = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new SupplyChallan();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.supplyChallan = entity;
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
