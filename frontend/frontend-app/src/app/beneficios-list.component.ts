import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BeneficioService } from './services/beneficio.service';
import { Beneficio } from './beneficio';

@Component({
  selector: 'app-beneficios-list',
  standalone: true,
  imports: [CommonModule],
  template: `
    <h1>Benefícios</h1>

    <p *ngIf="loading">Carregando...</p>
    <p *ngIf="error" style="color: red;">Erro ao carregar.</p>

    <table *ngIf="beneficios.length">
      <thead>
        <tr>
          <th>ID</th>
          <th>Nome</th>
          <th>Descrição</th>
          <th>Valor</th>
          <th>Ativo</th>
        </tr>
      </thead>
      <tbody>
        <tr *ngFor="let b of beneficios">
          <td>{{ b.id }}</td>
          <td>{{ b.nome }}</td>
          <td>{{ b.descricao }}</td>
          <td>{{ b.valor }}</td>
          <td>{{ b.ativo ? 'Sim' : 'Não' }}</td>
        </tr>
      </tbody>
    </table>

    <p *ngIf="!beneficios.length && !loading">Nenhum benefício encontrado.</p>
  `,
})
export class BeneficiosListComponent implements OnInit {
  beneficios: Beneficio[] = [];
  loading = false;
  error = false;

  constructor(private service: BeneficioService) {}

  ngOnInit(): void {
    this.loading = true;
    this.service.listar().subscribe({
      next: dados => {
        this.beneficios = dados;
        this.loading = false;
      },
      error: () => {
        this.error = true;
        this.loading = false;
      }
    });
  }
}
