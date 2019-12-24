/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialPoUpdateComponent } from 'app/entities/commercial-po/commercial-po-update.component';
import { CommercialPoService } from 'app/entities/commercial-po/commercial-po.service';
import { CommercialPo } from 'app/shared/model/commercial-po.model';

describe('Component Tests', () => {
    describe('CommercialPo Management Update Component', () => {
        let comp: CommercialPoUpdateComponent;
        let fixture: ComponentFixture<CommercialPoUpdateComponent>;
        let service: CommercialPoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialPoUpdateComponent]
            })
                .overrideTemplate(CommercialPoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CommercialPoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommercialPoService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CommercialPo(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.commercialPo = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CommercialPo();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.commercialPo = entity;
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
