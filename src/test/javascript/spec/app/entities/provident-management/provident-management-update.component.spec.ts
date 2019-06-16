/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { ProvidentManagementUpdateComponent } from 'app/entities/provident-management/provident-management-update.component';
import { ProvidentManagementService } from 'app/entities/provident-management/provident-management.service';
import { ProvidentManagement } from 'app/shared/model/provident-management.model';

describe('Component Tests', () => {
    describe('ProvidentManagement Management Update Component', () => {
        let comp: ProvidentManagementUpdateComponent;
        let fixture: ComponentFixture<ProvidentManagementUpdateComponent>;
        let service: ProvidentManagementService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [ProvidentManagementUpdateComponent]
            })
                .overrideTemplate(ProvidentManagementUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProvidentManagementUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProvidentManagementService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ProvidentManagement(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.providentManagement = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ProvidentManagement();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.providentManagement = entity;
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
