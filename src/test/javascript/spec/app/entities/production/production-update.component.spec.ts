/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { ProductionUpdateComponent } from 'app/entities/production/production-update.component';
import { ProductionService } from 'app/entities/production/production.service';
import { Production } from 'app/shared/model/production.model';

describe('Component Tests', () => {
    describe('Production Management Update Component', () => {
        let comp: ProductionUpdateComponent;
        let fixture: ComponentFixture<ProductionUpdateComponent>;
        let service: ProductionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [ProductionUpdateComponent]
            })
                .overrideTemplate(ProductionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProductionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductionService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Production(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.production = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Production();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.production = entity;
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
